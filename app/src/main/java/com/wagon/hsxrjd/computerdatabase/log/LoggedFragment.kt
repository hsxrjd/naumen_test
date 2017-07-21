package com.wagon.hsxrjd.computerdatabase.log

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.View

/**
 * Created by erychkov on 7/19/17.
 */
abstract class LoggedFragment : Fragment(), LoggedClass {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Logger.logger.debug(getClassName(), this::onViewCreated.name)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        Logger.logger.debug(getClassName(), this::onDestroyView.name)
        super.onDestroyView()
    }

    override fun onDestroy() {
        Logger.logger.debug(getClassName(), this::onDestroy.name)
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.logger.debug(getClassName(), this::onCreate.name)
        super.onCreate(savedInstanceState)
    }

    override fun onDetach() {
        Logger.logger.debug(getClassName(), this::onDetach.name)
        super.onDetach()
    }

    override fun onAttach(context: Context?) {
        Logger.logger.debug(getClassName(), this::onAttach.name)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Logger.logger.debug(getClassName(), this::onActivityCreated.name)
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.logger.debug(getClassName(), this::onActivityResult.name)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        Logger.logger.debug(getClassName(), this::onHiddenChanged.name)
        super.onHiddenChanged(hidden)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        Logger.logger.debug(getClassName(), this::onCreateContextMenu.name)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        Logger.logger.debug(getClassName(), this::onMultiWindowModeChanged.name)
        super.onMultiWindowModeChanged(isInMultiWindowMode)
    }

    override fun onPause() {
        Logger.logger.debug(getClassName(), this::onPause.name)
        super.onPause()
    }

    override fun onResume() {
        Logger.logger.debug(getClassName(), this::onResume.name)
        super.onResume()
    }

    override fun onStart() {
        Logger.logger.debug(getClassName(), this::onStart.name)
        super.onStart()
    }

    override fun onStop() {
        Logger.logger.debug(getClassName(), this::onStop.name)
        super.onStop()
    }

    override fun onLowMemory() {
        Logger.logger.debug(getClassName(), this::onLowMemory.name)
        super.onLowMemory()
    }

    override fun onDestroyOptionsMenu() {
        Logger.logger.debug(getClassName(), this::onDestroyOptionsMenu.name)
        super.onDestroyOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Logger.logger.debug(getClassName(), this::onCreateOptionsMenu.name)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Logger.logger.debug(getClassName(), this::onViewStateRestored.name)
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Logger.logger.debug(getClassName(), this::onSaveInstanceState.name)
        super.onSaveInstanceState(outState)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        Logger.logger.debug(getClassName(), this::onConfigurationChanged.name)
        super.onConfigurationChanged(newConfig)
    }

    override fun onOptionsMenuClosed(menu: Menu?) {
        Logger.logger.debug(getClassName(), this::onOptionsMenuClosed.name)
        super.onOptionsMenuClosed(menu)
    }

    override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        Logger.logger.debug(getClassName(), this::onInflate.name)
        super.onInflate(context, attrs, savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        Logger.logger.debug(getClassName(), this::onPrepareOptionsMenu.name)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        Logger.logger.debug(getClassName(), this::onAttachFragment.name)
        super.onAttachFragment(childFragment)
    }
}