package com.wagon.hsxrjd.computerdatabase.log

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by erychkov on 7/19/17.
 */
abstract class LoggedActivity : AppCompatActivity(), LoggedClass {
    override fun onAttachedToWindow() {
        Logger.logger.debug(getClassName(), this::onAttachedToWindow.name)
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        Logger.logger.debug(getClassName(), this::onDetachedFromWindow.name)
        super.onDetachedFromWindow()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        Logger.logger.debug(getClassName(), "onCreate")

        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        Logger.logger.debug(getClassName(), this::onStart.name)
        super.onStart()
    }

    override fun onStop() {
        Logger.logger.debug(getClassName(), this::onStop.name)
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.logger.debug(getClassName(), "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        Logger.logger.debug(getClassName(), this::onDestroy.name)
        super.onDestroy()
    }

    override fun onPause() {
        Logger.logger.debug(getClassName(), this::onPause.name)
        super.onPause()
    }

    override fun onRestart() {
        Logger.logger.debug(getClassName(), this::onRestart.name)
        super.onRestart()
    }

    override fun onResume() {
        Logger.logger.debug(getClassName(), this::onResume.name)
        super.onResume()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        Logger.logger.debug(getClassName(), "onAttachFragment")
        super.onAttachFragment(fragment)
    }

    override fun attachBaseContext(newBase: Context?) {
        Logger.logger.debug(getClassName(), this::attachBaseContext.name)
        super.attachBaseContext(newBase)
    }

    override fun closeContextMenu() {
        Logger.logger.debug(getClassName(), this::closeContextMenu.name)
        super.closeContextMenu()
    }

    override fun closeOptionsMenu() {
        Logger.logger.debug(getClassName(), this::closeOptionsMenu.name)
        super.closeOptionsMenu()
    }

    override fun finish() {
        Logger.logger.debug(getClassName(), this::finish.name)
        super.finish()
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        Logger.logger.debug(getClassName(), "startActivityForResult")
        super.startActivityForResult(intent, requestCode)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        Logger.logger.debug(getClassName(), "startActivityForResult")
        super.startActivityForResult(intent, requestCode, options)
    }

    override fun startActivity(intent: Intent?) {
        Logger.logger.debug(getClassName(), "startActivity")
        super.startActivity(intent)
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        Logger.logger.debug(getClassName(), "startActivity")
        super.startActivity(intent, options)
    }
}