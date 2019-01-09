package karrel.kr.co.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_rdb.*


class RealtimeDatabaseActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rdb)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("message")

        setupButtonEvents()

        setupDBEventListener()
    }

    private fun setupDBEventListener() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                addLog(value!!)

                println("Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                println("Failed to read value : ${error.toException()}")

                addLog(error.message)
            }
        })
    }

    private fun addLog(message: String) {
        val str = log.text.toString()
        println("addLog > str : $str")
        log.text = "$message\n$str"
        println("addLog > str2 : $message\n$str")
    }

    private fun setupButtonEvents() {
        fireHelloworld.setOnClickListener {
            fireHelloworldToDB()
        }
    }

    private fun fireHelloworldToDB() {
        myRef.setValue("Hello, World!")
    }
}
