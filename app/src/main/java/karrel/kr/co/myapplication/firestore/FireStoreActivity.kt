package karrel.kr.co.myapplication.firestore

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import karrel.kr.co.myapplication.R
import kotlinx.android.synthetic.main.activity_fire_store.*
import java.util.*
import karrel.kr.co.myapplication.R.id.collection
import karrel.kr.co.myapplication.firestore.model.City




class FireStoreActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fire_store)


        writeData.setOnClickListener { writeData() }
        writeData2.setOnClickListener { writeData2() }
        readData.setOnClickListener { readData() }

        collection.setOnClickListener { collection() }
        dataType.setOnClickListener { dataType() }
        city.setOnClickListener { city() }


    }

    // https://firebase.google.com/docs/firestore/manage-data/add-data?hl=ko
    private fun city(){
        val city = City("Los Angeles", "CA", "USA", false, 5000000L)
        db.collection("cities").document("LA").set(city)

    }

    // https://firebase.google.com/docs/firestore/manage-data/add-data?hl=ko
    private fun dataType() {
        val docData: HashMap<String, Any> = HashMap()
        docData.put("stringExample", "Hello world!")
        docData.put("booleanExample", true)
        docData.put("numberExample", 3.14159265)
        docData.put("dateExample", Date())
        docData.put("listExample", Arrays.asList(1, 2, 3))
//        docData.put("nullExample", null)

        val nestedData: HashMap<String, Any> = HashMap()
        nestedData.put("a", 5)
        nestedData.put("b", true)

        docData.put("objectExample", nestedData)

        db.collection("data").document("one")
                .set(docData)
//                .addOnSuccessListener { Log.d(FragmentActivity.TAG, "DocumentSnapshot successfully written!") }
//                .addOnFailureListener { e -> Log.w(FragmentActivity.TAG, "Error writing document", e) }

    }

    private fun simpleQueries() {
        // [START simple_queries]
        // Create a reference to the cities collection
        val citiesRef = db.collection("cities")

        // Create a query against the collection.
        val query = citiesRef.whereEqualTo("state", "CA")
        // [END simple_queries]

        // [START simple_query_capital]
        val capitalCities = db.collection("cities").whereEqualTo("capital", true)
        // [END simple_query_capital]

        // [START example_filters]
        citiesRef.whereEqualTo("state", "CA")
        citiesRef.whereLessThan("population", 100000)
        citiesRef.whereGreaterThanOrEqualTo("name", "San Francisco")
        // [END example_filters]
    }

    private fun collection() {
        val usersCollectionRef = db.collection("users")


        addLog(usersCollectionRef.toString())

        usersCollectionRef.get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result!!) {
                            toast(document.id + " => " + document.data)
                        }
                    } else {
                        toast("Error getting documents. ${it.exception}")
                    }
                }

    }

    private fun readData() {
        db.collection("users")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            toast(document.id + " => " + document.data)

                        }
                    } else {
                        toast("Error getting documents. ${task.exception}")
                    }
                }

    }

    private fun writeData() {
        val user: HashMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    toast("DocumentSnapshot added with ID: ${documentReference.id}")

                }
                .addOnFailureListener { e ->
                    toast("Error adding document ${e.message}")
                }

    }

    private fun writeData2() {
        val user: HashMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["middle"] = "Mathison"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    toast("DocumentSnapshot added with ID: ${documentReference.id}")

                }
                .addOnFailureListener { e ->
                    toast("Error adding document ${e.message}")
                }

    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        addLog(msg)
    }

    private fun addLog(msg: String) {
        val logStr = log.text.toString()
        log.text = "$msg\n$logStr"
    }
}

