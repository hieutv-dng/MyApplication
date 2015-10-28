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
public class Splitter implements Parcelable {
    public static final Creator<Splitter> CREATOR = new Creator<Splitter>() {
        public Splitter createFromParcel(Parcel source) {
            return new Splitter(source);
        }

        public Splitter[] newArray(int size) {
            return new Splitter[size];
        }
    };

    private int MA_SPLITTER;
    private int DUNG_LUONG;
    private int LOAI_DAUVAO;
    private String TEN_SPLITTER;
    private String MA_KC;
    private String MA_DV;

    protected Splitter(Parcel in) {
        this.MA_SPLITTER = in.readInt();
        this.DUNG_LUONG = in.readInt();
        this.LOAI_DAUVAO = in.readInt();
        this.TEN_SPLITTER = in.readString();
        this.MA_KC = in.readString();
        this.MA_DV = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.MA_SPLITTER);
        dest.writeInt(this.DUNG_LUONG);
        dest.writeInt(this.LOAI_DAUVAO);
        dest.writeString(this.TEN_SPLITTER);
        dest.writeString(this.MA_KC);
        dest.writeString(this.MA_DV);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
