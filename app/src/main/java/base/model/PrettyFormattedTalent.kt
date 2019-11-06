package base.model

import android.os.Parcel
import android.os.Parcelable

/**
 * used for ready to show data beans in adapter
 */
data class PrettyFormattedTalent(
    var field: String?,
    var title: String?,
    var descr: String?,
    var experience: String?,
    var address: String?,
    var phone: String?,
    var price: String?,
    var email: String?,
    var time: String?,
    var seen: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
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
        parcel.writeString(field)
        parcel.writeString(title)
        parcel.writeString(descr)
        parcel.writeString(experience)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(price)
        parcel.writeString(email)
        parcel.writeString(time)
        parcel.writeString(seen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PrettyFormattedTalent> {
        override fun createFromParcel(parcel: Parcel): PrettyFormattedTalent {
            return PrettyFormattedTalent(parcel)
        }

        override fun newArray(size: Int): Array<PrettyFormattedTalent?> {
            return arrayOfNulls(size)
        }
    }
}
