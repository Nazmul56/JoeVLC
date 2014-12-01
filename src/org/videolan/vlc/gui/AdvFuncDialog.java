/*****************************************************************************
 * AdvFuncDialog.java
 *****************************************************************************
 * Copyright © 2012 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/
package org.videolan.vlc.gui;

import java.util.Calendar;

import org.videolan.vlc.LibVLC;
import org.videolan.vlc.Util;
import org.videolan.vlc.gui.SpeedSelectorDialog;
import org.videolan.vlc.gui.TimeSleepDialog;

import the.joevlc.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdvFuncDialog extends Dialog {
    public final static String TAG = "VLC/AdvFuncPopupWindow";

    private static View mAdvFuncView;

    private ImageButton mSleep;
    private TimeSleepDialog mTimeSleepDialog;
    private ImageButton mSpeed;
    private TextView mSpeedInfo;
    private SpeedSelectorDialog mSpeedSelectorDialog;

    /**
     * Put all advance functionality here
     */
    public AdvFuncDialog(Activity activity) {
        super(activity);
        setOwnerActivity(activity);

        LayoutInflater inflater = LayoutInflater.from(getOwnerActivity());
        mAdvFuncView = inflater.inflate(R.layout.advance_function,
                (ViewGroup) findViewById(R.id.adv_func));

        setContentView(mAdvFuncView);
        setTitle(getOwnerActivity().getString(R.string.tools));
        setCanceledOnTouchOutside(true);

        // Init Sleep function
        mSleep = (ImageButton) mAdvFuncView.findViewById(R.id.adv_func_sleep);
        mSleep.setOnClickListener(mSleepListener);

        // Init Speed function
        mSpeed = (ImageButton) mAdvFuncView.findViewById(R.id.adv_func_speed);
        mSpeed.setOnClickListener(mSpeedLabelListener);
        mSpeedInfo = (TextView) mAdvFuncView.findViewById(R.id.adv_func_speed_info);
        mSpeedInfo.setText(getSpeedInfo());
    }

    private final View.OnClickListener mSleepListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            mTimeSleepDialog = new TimeSleepDialog(getOwnerActivity(), hour, minute);
        };
    };

    private final View.OnClickListener mSpeedLabelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSpeedSelectorDialog = new SpeedSelectorDialog(getOwnerActivity());
            mSpeedSelectorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mSpeedInfo.setText(getSpeedInfo());
                }
            });
            mSpeedSelectorDialog.show();
        }
    };

    /**
     * Return play speed
     */
    private String getSpeedInfo() {
        LibVLC libVLC = LibVLC.getExistingInstance();
        if (libVLC != null)
            return Util.formatRateString(libVLC.getRate());
        else
            return "";
    }

    public void destroyAdvFuncDialog() {

        // Dismiss secondary window
        if(mTimeSleepDialog != null) {
            if(mTimeSleepDialog.isShowing()) {
                mTimeSleepDialog.dismiss();
            }
        }
        if(mSpeedSelectorDialog != null) {
            if (mSpeedSelectorDialog.isShowing()) {
                mSpeedSelectorDialog.dismiss();
            }
        }

        // Dismiss main window
        if(isShowing())
            dismiss();
    }
}
