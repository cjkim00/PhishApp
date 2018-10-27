package cjkim00.tcss450.uw.edu.phishapp.SetList;

import java.io.Serializable;

public class SetLists implements Serializable {
    private final String mLongDate;
    //private final String mUrl;
    private final String mVenue;
    private final String mLocation;
    private final String mSetListData;
    private final String mSetListNodes;
    private final String mUrl;

    public static class Builder {
        private final String mLongDate;
        private final String mVenue;
        private String mLocation = "";
        private String mSetListData = "";
        private String mSetListNodes = "";
        private String mUrl;
        public Builder(String longDate, String location, String venue) {
            this.mLongDate = longDate;
            this.mLocation = location;
            this.mVenue = venue;
        }

        public Builder addSetList(final String val) {
            mSetListData = val;
            return this;
        }

        public Builder addSetListNodes(final String val) {
            mSetListNodes = val;
            return this;
        }

        public Builder addLocation(final String val) {
            mLocation = val;
            return this;
        }
        public Builder addUrl(final String val) {
            mUrl = val;
            return this;
        }

        public SetLists build() {
            return new SetLists(this);
        }
    }

    private SetLists(final Builder builder) {
        this.mLongDate = builder.mLongDate;
        this.mUrl = builder.mUrl;
        this.mVenue = builder.mVenue;
        this.mLocation = builder.mLocation;
        this.mSetListData = builder.mSetListData;
        this.mSetListNodes = builder.mSetListNodes;

    }

    public String getLongDate() {
        return mLongDate;
    }
    public String getLocation() {
        return mLocation;
    }
    public String getVenue() {
        return mVenue;
    }
    public String getSetListData() {
        return mSetListData;
    }
    public String getSetListNodes() {
        return mSetListNodes;
    }

    public String getUrl() {
        return mUrl;
    }

}
