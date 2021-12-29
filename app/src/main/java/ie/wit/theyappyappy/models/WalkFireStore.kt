package ie.wit.theyappyappy.models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.theyappyappy.helpers.readImageFromPath
import timber.log.Timber.i
import java.io.ByteArrayOutputStream
import java.io.File

class WalkFireStore(val context: Context) : WalkStore {
    val walks = ArrayList<WalkModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override suspend fun findAll(): List<WalkModel> {
        return walks
    }

    override suspend fun findById(id: Long): WalkModel? {
        val foundWalk: WalkModel? = walks.find { w -> w.id == id }
        return foundWalk
    }

    override suspend fun create(walk: WalkModel) {
        val key = db.child("users").child(userId).child("walks").push().key
        key?.let {
            walk.fbId = key
            walks.add(walk)
            db.child("users").child(userId).child("walks").child(key).setValue(walk)
            updateImage(walk)

        }
    }

    override suspend fun update(walk: WalkModel) {
        var foundWalk: WalkModel? = walks.find { w -> w.fbId == walk.fbId }
        if (foundWalk != null) {
            foundWalk.title = walk.title
            foundWalk.description = walk.description
            foundWalk.image = walk.image
            foundWalk.lead_required = walk.lead_required
            foundWalk.bins_provided = walk.bins_provided
            foundWalk.length = walk.length
            foundWalk.type = walk.type
            foundWalk.lat = walk.lat
            foundWalk.lng = walk.lng
            foundWalk.zoom = walk.zoom
        }

        db.child("users").child(userId).child("walks").child(walk.fbId).setValue(walk)
        if(walk.image.length > 0){
            updateImage(walk)
        }
    }

    override suspend fun delete(walk: WalkModel) {
        db.child("users").child(userId).child("walks").child(walk.fbId).removeValue()
        walks.remove(walk)
    }

    override suspend fun clear() {
        walks.clear()
    }

    fun fetchWalks(walksReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(walks) {
                    it.getValue<WalkModel>(
                        WalkModel::class.java
                    )
                }
                walksReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        st = FirebaseStorage.getInstance().reference
        db = FirebaseDatabase.getInstance().reference
        walks.clear()
        db.child("users").child(userId).child("walks")
            .addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImage(walk: WalkModel) {
        if (walk.image != "") {
            val fileName = File(walk.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, walk.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        walk.image = it.toString()
                        db.child("users").child(userId).child("walks").child(walk.fbId).setValue(walk)
                    }
                }
            }
        }
    }
}