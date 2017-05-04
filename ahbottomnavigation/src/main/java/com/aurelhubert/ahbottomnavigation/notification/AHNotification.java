package com.aurelhubert.ahbottomnavigation.notification;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author repitch
 */
public class AHNotification implements Parcelable {

	public static final Creator<AHNotification> CREATOR = new Creator<AHNotification>() {
		@Override
		public AHNotification createFromParcel(Parcel source) {
			return new AHNotification(source);
		}

		@Override
		public AHNotification[] newArray(int size) {
			return new AHNotification[size];
		}
	};
	@Nullable
	private String text; // can be null, so notification will not be shown
	@ColorInt
	private int textColor; // if 0 then use default value
	@ColorInt
	private int backgroundColor; // if 0 then use default value
	private boolean indicatorOnly; // only show small indicator instead of a notification with a number.

	public AHNotification() {
		// empty
	}

	protected AHNotification(Parcel in) {
		this.text = in.readString();
		this.textColor = in.readInt();
		this.backgroundColor = in.readInt();
		this.indicatorOnly = in.readByte() != 0;
	}

	public static AHNotification justText(String text) {
		return new Builder().setText(text).setIndicatorOnly(false).build();
	}

	public static AHNotification justIndicator() {
		return new Builder().setText(null).setIndicatorOnly(true).build();
	}

	public static List<AHNotification> generateEmptyList(int size) {
		List<AHNotification> notificationList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			notificationList.add(new AHNotification());
		}
		return notificationList;
	}

	public boolean isIndicatorOnly() {
		return indicatorOnly;
	}

	public boolean isEmpty() {
		return TextUtils.isEmpty(text);
	}

	@Nullable
	public String getText() {
		return text;
	}

	public int getTextColor() {
		return textColor;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.text);
		dest.writeInt(this.textColor);
		dest.writeInt(this.backgroundColor);
		dest.writeByte(this.indicatorOnly ? (byte) 1 : (byte) 0);
	}

	public static class Builder {
		@Nullable
		private String text;
		@ColorInt
		private int textColor;
		@ColorInt
		private int backgroundColor;

		private boolean indicatorOnly;

		public Builder setText(String text) {

			if (!TextUtils.isEmpty(text) && indicatorOnly) {
				throw new RuntimeException("Notification and Indicator should not be shown at the same time");
			}
			this.text = text;
			return this;
		}

		public Builder setIndicatorOnly(boolean indicatorOnly) {
			if (!TextUtils.isEmpty(text) && indicatorOnly) {
				throw new RuntimeException("Notification and Indicator should not be shown at the same time");
			}

			this.indicatorOnly = indicatorOnly;
			return this;
		}


		public Builder setTextColor(@ColorInt int textColor) {
			this.textColor = textColor;
			return this;
		}

		public Builder setBackgroundColor(@ColorInt int backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		public AHNotification build() {
			if (!TextUtils.isEmpty(text) && indicatorOnly) {
				throw new RuntimeException("Notification and Indicator should not be shown at the same time");
			}

			AHNotification notification = new AHNotification();
			notification.text = text;
			notification.textColor = textColor;
			notification.backgroundColor = backgroundColor;
			notification.indicatorOnly = indicatorOnly;
			return notification;
		}
	}
}
