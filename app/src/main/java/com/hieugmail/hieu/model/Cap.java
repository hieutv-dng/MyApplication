package com.hieugmail.hieu.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Class object Cap.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class Cap implements Parcelable {
    public static final Creator<Cap> CREATOR = new Creator<Cap>() {
        public Cap createFromParcel(Parcel source) {
            return new Cap(source);
        }

        public Cap[] newArray(int size) {
            return new Cap[size];
        }
    };

    private String MA_CAP;
    private String TEN_CAP;
    private String MA_TDUONG;
    private String MA_KCD;
    private String MA_KCC;
    private String MA_DV;
    private int DL_THUCTE;
    private int DL_SDUNG;
    private int LOAI_CAP;
    private int MA_CHATLIEUCAP;
    private float CHIEU_DAI;

    public Cap() {
    }

    protected Cap(Parcel in) {
        this.MA_CAP = in.readString();
        this.TEN_CAP = in.readString();
        this.MA_TDUONG = in.readString();
        this.MA_KCD = in.readString();
        this.MA_KCC = in.readString();
        this.MA_DV = in.readString();
        this.DL_THUCTE = in.readInt();
        this.DL_SDUNG = in.readInt();
        this.LOAI_CAP = in.readInt();
        this.MA_CHATLIEUCAP = in.readInt();
        this.CHIEU_DAI = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MA_CAP);
        dest.writeString(this.TEN_CAP);
        dest.writeString(this.MA_TDUONG);
        dest.writeString(this.MA_KCD);
        dest.writeString(this.MA_KCC);
        dest.writeString(this.MA_DV);
        dest.writeInt(this.DL_THUCTE);
        dest.writeInt(this.DL_SDUNG);
        dest.writeInt(this.LOAI_CAP);
        dest.writeInt(this.MA_CHATLIEUCAP);
        dest.writeFloat(this.CHIEU_DAI);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
