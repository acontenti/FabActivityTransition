package com.github.fabactivitytransition;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Project s.a.s. on 27/01/2016.
 * Copyright © 1996, 2016 PROJECT s.a.s. All Rights Reserved.
 */
public class FabActivityTransition {

	private Activity activity;
	private ImageView fab;
	private SheetLayout sheetLayout;
	private StartActivityCallback startActivityCallback;
	private Bundle properties;

	private FabActivityTransition(Activity activity) {
		this.activity = activity;
	}

	public void start() {
		sheetLayout.expandFab();
	}

	public void reverse() {
		sheetLayout.contractFab();
	}

	private void setFab(ImageView fab) {
		this.fab = fab;
	}

	private void setStartActivityCallback(StartActivityCallback startActivityCallback) {
		this.startActivityCallback = startActivityCallback;
	}

	private void setProperties(Bundle properties) {
		this.properties = properties;
	}

	private void build() {
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
		sheetLayout = new SheetLayout(activity, properties);
		sheetLayout.setFab(fab);
		sheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
			@Override
			public void onFabAnimationEnd() {
				startActivityCallback.onNeedToStartActivity();
			}
		});
		viewGroup.addView(sheetLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	}

	public interface StartActivityCallback {
		void onNeedToStartActivity();
	}

	public static class Builder {
		private FabActivityTransition fabActivityTransition;
		private Bundle properties = new Bundle();

		public Builder(Activity activity) {
			fabActivityTransition = new FabActivityTransition(activity);
		}

		public Builder withFab(ImageView fab) {
			fabActivityTransition.setFab(fab);
			return this;
		}

		public Builder withStartActivityCallback(StartActivityCallback startActivityCallback) {
			fabActivityTransition.setStartActivityCallback(startActivityCallback);
			return this;
		}

		public Builder withColor(@ColorInt int color) {
			properties.putInt(SheetLayout.PROPERTY_COLOR, color);
			return this;
		}

		public Builder withGravity(@SheetLayout.GravityInt int gravity) {
			properties.putInt(SheetLayout.PROPERTY_GRAVITY, gravity);
			return this;
		}

		public Builder withAnimationDuration(int duration) {
			properties.putInt(SheetLayout.PROPERTY_ANIMATION_DURATION, duration);
			return this;
		}

		public Builder withFabSize(int size) {
			properties.putInt(SheetLayout.PROPERTY_FAB_SIZE, size);
			return this;
		}

		public FabActivityTransition build() {
			fabActivityTransition.setProperties(properties);
			fabActivityTransition.build();
			return fabActivityTransition;
		}
	}
}