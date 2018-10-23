package fortnite.eugene.com.fortnitetracker.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StatsItem implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public StatsItem() {
    }

    protected StatsItem(Parcel in) {
    }

    public static final Parcelable.Creator<StatsItem> CREATOR = new Parcelable.Creator<StatsItem>() {
        @Override
        public StatsItem createFromParcel(Parcel source) {
            return new StatsItem(source);
        }

        @Override
        public StatsItem[] newArray(int size) {
            return new StatsItem[size];
        }
    };
}
