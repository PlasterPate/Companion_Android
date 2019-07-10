/*
 *
 *  Copyright (C) 2017 Thibault Fighiera
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tfighiera.revealactivity

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dalisyron.companion.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*

class RevealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        RevealCircleAnimatorHelper
                .create(this)
            .start(home_constraint,R.color.black_theme2,R.color.yellow_theme2)
        setShowFragmentButton()
    }

    private fun setShowFragmentButton() {
        login_button.setOnClickListener { view ->
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, RevealFragment.newInstance(sourceView = view))
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    companion object {
        /**
         * Create a new [Intent] of [RevealActivity]
         * @param context
         */
        fun newIntent(context: Context, sourceView: View? = null): Intent {
            return Intent(context, RevealActivity::class.java).also {
                RevealCircleAnimatorHelper.addBundleValues(it, sourceView)
            }
        }
    }
}