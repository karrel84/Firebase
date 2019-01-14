package karrel.kr.co.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import karrel.kr.co.myapplication.realtime_database.RealtimeDatabaseActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        RealtimeDatabase.setOnClickListener { startActivity(Intent(this, RealtimeDatabaseActivity::class.java)) }
    }
}
