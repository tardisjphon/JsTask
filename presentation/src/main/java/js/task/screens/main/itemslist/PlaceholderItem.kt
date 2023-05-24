package js.task.screens.main.itemslist

import android.os.Parcel
import android.os.Parcelable

data class PlaceholderItem(
    val id : Int,
    val userName : String,
    val url : String,
    val apiName : String,
    val isSelected : Boolean = false
) : Parcelable
{
    constructor(parcel : Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readByte() != 0.toByte()
    )

    override fun toString() : String = userName
    override fun writeToParcel(parcel : Parcel, flags : Int)
    {
        parcel.writeInt(id)
        parcel.writeString(userName)
        parcel.writeString(url)
        parcel.writeString(apiName)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents() : Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceholderItem>
    {
        override fun createFromParcel(parcel : Parcel) : PlaceholderItem
        {
            return PlaceholderItem(parcel)
        }

        override fun newArray(size : Int) : Array<PlaceholderItem?>
        {
            return arrayOfNulls(size)
        }
    }
}