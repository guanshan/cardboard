package com.wormhole.vr.cardboard;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class UiUtils {
	private static final String CARDBOARD_WEBSITE = "http://google.com/cardboard/cfg";
	private static final String CARDBOARD_CONFIGURE_ACTION = "com.google.vrtoolkit.cardboard.CONFIGURE";
	private static final String INTENT_KEY = "intent";

	public static void launchOrInstallCardboard(Context context, boolean confirm) {
		PackageManager pm = context.getPackageManager();
		Intent settingsIntent = new Intent();
		settingsIntent.setAction(CARDBOARD_CONFIGURE_ACTION);

		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(
				settingsIntent, 0);
		List intentsToGoogleCardboard = new ArrayList();
		for (ResolveInfo info : resolveInfos) {
			String pkgName = info.activityInfo.packageName;
			if (pkgName.startsWith("com.google.")) {
				Intent intent = new Intent(settingsIntent);
				intent.setClassName(pkgName, info.activityInfo.name);
				intentsToGoogleCardboard.add(intent);
			}
		}

		if (intentsToGoogleCardboard.isEmpty()) {
			showInstallDialog(context);
		} else {
			Intent intent = intentsToGoogleCardboard.size() == 1 ? (Intent) intentsToGoogleCardboard
					.get(0) : settingsIntent;
			if (confirm) {
				showConfigureDialog(context, intent);
			} else
				context.startActivity(intent);
		}
	}

	static void launchOrInstallCardboard(Context context) {
		launchOrInstallCardboard(context, true);
	}

	private static void showInstallDialog(Context context) {
		FragmentManager fragmentManager = ((Activity) context)
				.getFragmentManager();
		new InstallSettingsDialogFragment().show(fragmentManager,
				"InstallCardboardDialog");
	}

	private static void showConfigureDialog(Context context, Intent intent) {
		FragmentManager fragmentManager = ((Activity) context)
				.getFragmentManager();
		DialogFragment dialog = new ConfigureSettingsDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(INTENT_KEY, intent);
		dialog.setArguments(bundle);
		dialog.show(fragmentManager, "ConfigureCardboardDialog");
	}

	public static class ConfigureSettingsDialogFragment extends DialogFragment {
		private Intent intent;
		private final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				try {
					UiUtils.ConfigureSettingsDialogFragment.this
							.getActivity()
							.startActivity(
									UiUtils.ConfigureSettingsDialogFragment.this.intent);
				} catch (ActivityNotFoundException e) {
					UiUtils.showInstallDialog(UiUtils.ConfigureSettingsDialogFragment.this
							.getActivity());
				}
			}
		};

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.intent = ((Intent) getArguments().getParcelable(INTENT_KEY));
		}

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(Strings.getString("DIALOG_TITLE"))
					.setMessage(Strings.getString("DIALOG_MESSAGE_SETUP"))
					.setPositiveButton(Strings.getString("SETUP_BUTTON"),
							this.listener)
					.setNegativeButton(Strings.getString("CANCEL_BUTTON"), null);
			return builder.create();
		}
	}

	public static class InstallSettingsDialogFragment extends DialogFragment {
		private final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				try {
					UiUtils.InstallSettingsDialogFragment.this
							.getActivity()
							.startActivity(
									new Intent(
											"android.intent.action.VIEW",
											Uri.parse(CARDBOARD_WEBSITE)));
				} catch (ActivityNotFoundException e) {
					Toast.makeText(
							UiUtils.InstallSettingsDialogFragment.this
									.getActivity().getApplicationContext(),
							Strings.getString("NO_BROWSER_TEXT"), 1).show();
				}
			}
		};

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(Strings.getString("DIALOG_TITLE"))
					.setMessage(
							Strings.getString("DIALOG_MESSAGE_NO_CARDBOARD"))
					.setPositiveButton(
							Strings.getString("GO_TO_PLAYSTORE_BUTTON"),
							this.listener)
					.setNegativeButton(Strings.getString("CANCEL_BUTTON"), null);
			return builder.create();
		}
	}
}