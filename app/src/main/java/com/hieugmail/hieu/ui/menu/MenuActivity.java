package com.hieugmail.hieu.ui.menu;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hieugmail.hieu.BaseActivity;
import com.hieugmail.hieu.R;
import com.hieugmail.hieu.service.googleapi.AddressResponse;
import com.hieugmail.hieu.service.googleapi.ApiGoogle;
import com.hieugmail.hieu.ui.maps.CheckGPSEnabled;
import com.hieugmail.hieu.ui.maps.GoogleServiceUtil;
import com.hieugmail.hieu.ui.maps.LocationHelper;
import com.hieugmail.hieu.util.KeyboardUtil;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Screen Main
 */
@EActivity(R.layout.activity_menu)
public class MenuActivity extends BaseActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, LocationHelper.OnLocationUpdate, OnMapReadyCallback {
    private static final String TAG = MenuActivity.class.getName();
    private static final int CAMERA_ZOOM_LOCATION_CURRENT = 15;
    private static final int CAMERA_ZOOM_CITY = 5;

    private SupportMapFragment mSupportMapFragment;
    private ProgressDialog mProgressLoading;
    private Location mMyLocation;
    private GoogleMap mGoogleMap;
    private AsyncTask mAsyncMap;
    private Marker mMarker;
    //This is lat & lng of Ha Noi capital of Viet Nam.
    private double mLatHaNoi = 21.027580;
    private double mLngHaNoi = 105.834817;

    @ViewById
    DrawerLayout mMyPageDrawerLayout;
    @ViewById
    ImageView mImgClearSearch;
    @ViewById
    EditText mEdtSearch;

    @Override
    protected void init() {
        mProgressLoading = new ProgressDialog(this);
        mProgressLoading.setMessage(getString(R.string.msg_please_wait));
        mProgressLoading.setCancelable(false);

        //init Google PlayService
        if (GoogleServiceUtil.isGooglePlayServicesAvailable(this)) {
            if (mSupportMapFragment == null) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(mLatHaNoi, mLngHaNoi))
                        .zoom(CAMERA_ZOOM_CITY)
                        .build();
                mSupportMapFragment = SupportMapFragment.newInstance(new GoogleMapOptions().camera(cameraPosition));
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.mMap, mSupportMapFragment).commit();
                mSupportMapFragment.getMapAsync(this);
            }
        }
        //init Gps
        initGps();

        // call method catch event press button search on keyboard.
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchAddress();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * This method will check GPS or Network Location already or not yet.
     * After that, We can found position for this.
     */
    private void initGps() {
        if (CheckGPSEnabled.checkGPSOpen(this)) {
            if (mMyLocation == null) {
                mProgressLoading.show();
                LocationHelper locationHelper = new LocationHelper(this);
                locationHelper.addRequestListener(this);
            }
        } else {
            CheckGPSEnabled.showGPSDisabledAlertToUser(this);
            // We need check enable location again when we can not get my location
            // Toast.makeText(this, "Không thể có được vị trí của bạn", Toast.LENGTH_SHORT).show();
            // We need sync map only first time, when mGoogleMap is null. This location will set to Japan country from here
            if (mGoogleMap == null) {
                mSupportMapFragment.getMapAsync(this);
            }
        }
    }

    /**
     * This method will set lat & lng of Google Map and go to this location via CameraZoom
     *
     * @param latitude     this is latitude
     * @param longitude    this is longitude
     * @param isShowMarker false: don't show marker, true: show marker
     */
    private void setDataGoogleMap(final double latitude, final double longitude, final boolean isShowMarker) {

        if (mAsyncMap == null) {
            mAsyncMap = new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Bitmap doInBackground(Void... params) {
                    return BitmapFactory.decodeResource(getResources(), R.drawable.ic_tab0);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    //Remove marker
                    if (mMarker != null) {
                        mMarker.remove();
                    }
                    // Save Lat & Lng research when camerazoom working
                    LatLng latLng = new LatLng(latitude, longitude);
                    CameraUpdate cameraUpdate;
                    if (isShowMarker) {
                        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM_LOCATION_CURRENT);
                        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                    } else {
                        cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
                    }

                    mGoogleMap.animateCamera(cameraUpdate);
                    mAsyncMap = null;
                }
            }.execute();
        } else {
            Toast.makeText(this, getString(R.string.msg_get_my_location), Toast.LENGTH_SHORT).show();
        }
    }

    private void searchAddress() {
        final String address = mEdtSearch.getText().toString();
        // hide keyboard if it showing
        View view = this.getCurrentFocus();
        if (view != null) {
            KeyboardUtil.hideKeyboard(this, view);
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, getString(R.string.msg_error_address_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressLoading.show();
        ApiGoogle.getInstance().getRequestGoogleMap().getLocation(address, new Callback<AddressResponse>() {
            @Override
            public void success(AddressResponse addressResponse, Response response) {
                mProgressLoading.dismiss();
                if (addressResponse.getResults() == null || addressResponse.getResults().isEmpty() || !addressResponse.getStatus().equals("OK")) {
                    Toast.makeText(MenuActivity.this, getString(R.string.msg_error_not_found_address), Toast.LENGTH_SHORT).show();
                    return;
                }
                double lat = addressResponse.getResults().get(0).getGeometry().getLocation().getLat();
                double lng = addressResponse.getResults().get(0).getGeometry().getLocation().getLng();
                setDataGoogleMap(lat, lng, true);
            }

            @Override
            public void failure(RetrofitError error) {
                mProgressLoading.dismiss();
                Toast.makeText(MenuActivity.this, getString(R.string.msg_error_not_found_address), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMyPageDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mMyPageDrawerLayout.closeDrawers();
        }
        initGps();
    }

    @Click(R.id.mImgMenu)
    void onMyPageClick() {
        mMyPageDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Click(R.id.mImgSearch)
    void onImgSearch() {
        searchAddress();
    }

    @Click(R.id.mImgClearSearch)
    void onImgClearSearchClick() {
        mEdtSearch.setText("");
    }

    @Click(R.id.mImgGps)
    void onImgGpsClick() {
        if (mMyLocation != null) {
            setDataGoogleMap(mMyLocation.getLatitude(), mMyLocation.getLongitude(), true);
        }

    }

    @AfterTextChange(R.id.mEdtSearch)
    void onEdtSearchChange(Editable text) {
        int number = text.length();
        mImgClearSearch.setVisibility(number > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (mMyPageDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mMyPageDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setDataGoogleMap(latLng.latitude, latLng.longitude, true);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationUpdated(Location location) {
        mProgressLoading.dismiss();
        mMyLocation = location;
        // We need sync map only first time, when mGoogleMap is null
        if (mGoogleMap != null) {
            mSupportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
        if (mMyLocation != null) {
            setDataGoogleMap(mMyLocation.getLatitude(), mMyLocation.getLongitude(), true);
        } else {
            // Can not get My location set default of Ha Noi City
            setDataGoogleMap(mLatHaNoi, mLngHaNoi, false);
        }
    }
}
