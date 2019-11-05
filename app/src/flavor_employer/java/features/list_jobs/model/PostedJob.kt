package features.list_jobs.model

import android.os.Parcel
import android.os.Parcelable

/**
 * used for ready to show data beans in adapter
 */
data class PostedJob(
    var id: String?,
    var title: String?,
    var field: String?,
    var descr: String?,
    var address: String?,
    var price: String?,
    var time: String?,
    var seen: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(field)
        parcel.writeString(descr)
        parcel.writeString(address)
        parcel.writeString(price)
        parcel.writeString(time)
        parcel.writeString(seen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostedJob> {
        override fun createFromParcel(parcel: Parcel): PostedJob {
            return PostedJob(parcel)
        }

        override fun newArray(size: Int): Array<PostedJob?> {
            return arrayOfNulls(size)
        }
    }
}