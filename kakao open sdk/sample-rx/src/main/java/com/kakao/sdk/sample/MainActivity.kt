/*
  Copyright 2020 Kakao Corp.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.kakao.sdk.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.kakao.sdk.sample.common.internal.DebugFragment
import com.kakao.sdk.sample.common.OpenFragmentRx

class MainActivity : AppCompatActivity() {
    val debugFragment = DebugFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container, OpenFragmentRx.newInstance(Bundle().apply {
            putLong("customMemo", 19388)
            putLong("customMessage", 19388)
        })).disallowAddToBackStack().commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_debug -> {
                if (debugFragment.isAdded) return true
                supportFragmentManager.beginTransaction()
                    .addToBackStack("main")
                    .add(
                        R.id.container,
                        debugFragment
                    ).commit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
