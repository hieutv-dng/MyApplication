package com.hieugmail.hieu.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Class object
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class KetCuoi implements Parcelable {
    public static final Creator<KetCuoi> CREATOR = new Creator<KetCuoi>() {
        public KetCuoi createFromParcel(Parcel source) {
            return new KetCuoi(source);
        }

        public KetCuoi[] newArray(int size) {
            return new KetCuoi[size];
        }
    };

    private String MA_KC;
    private String TEN_KC;
    private String DCHI_KC;
    private String MA_TDUONG;
    private String MA_DV;
    private int LOAI_KC;
    private boolean DAI_MDF;
    private long KINH_DO;
    private long VI_DO;

    public KetCuoi() {
    }

    protected KetCuoi(Parcel in) {
        this.MA_KC = in.readString();
        this.TEN_KC = in.readString();
        this.DCHI_KC = in.readString();
        this.MA_TDUONG = in.readString();
        this.MA_DV = in.readString();
        this.LOAI_KC = in.readInt();
        this.DAI_MDF = in.readByte() != 0;
        this.KINH_DO = in.readLong();
        this.VI_DO = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MA_KC);
        dest.writeString(this.TEN_KC);
        dest.writeString(this.DCHI_KC);
        dest.writeString(this.MA_TDUONG);
        dest.writeString(this.MA_DV);
        dest.writeInt(this.LOAI_KC);
        dest.writeByte(DAI_MDF ? (byte) 1 : (byte) 0);
        dest.writeLong(this.KINH_DO);
        dest.writeLong(this.VI_DO);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
