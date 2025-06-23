package com.example.moco2025team1.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moco2025team1.model.database.AppDatabase
import com.example.moco2025team1.model.entities.Prompt
import com.example.moco2025team1.model.stores.PromptStore
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

// you will not only have one single viewmodel, but maybe as many as you have screens
class PromptViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app.applicationContext)
    private val promptDao = db.promptDao()
    private val preferences = app.getSharedPreferences("prompt_preferences", Context.MODE_PRIVATE)
    private val promptStore = PromptStore(promptDao, preferences)

    //only viewmodel should update it
    private val _dailyPrompts = MutableStateFlow<List<Prompt>>(emptyList())
    // public read-only for UI
    val dailyPrompts: StateFlow<List<Prompt>> = _dailyPrompts

    private val firebaseDatabase =
        Firebase.database("https://moco-2025-team-1-default-rtdb.europe-west1.firebasedatabase.app")
    private val firebaseDatabaseRef = firebaseDatabase.getReference("message")
    val firebaseState = MutableLiveData("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            promptStore.init()
            _dailyPrompts.value = promptStore.getDailyPrompts()
        }

        addListener {
            firebaseState.postValue(it)
        }
    }

    //for testing
    fun refreshPrompts(fakeDate: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            _dailyPrompts.value = promptStore.getDailyPrompts(fakeDate)
        }
    }

    fun refreshPrompts() {
        viewModelScope.launch(Dispatchers.IO) {
            _dailyPrompts.value = promptStore.getDailyPrompts()
        }
    }

    fun insertPrompt(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            promptStore.insertPrompt(Prompt(content = content))
        }

        firebaseDatabaseRef.setValue(content)
    }

    private fun addListener(onDataChange: (value: String) -> Unit) {
        firebaseDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d("firebase", "Value is: $value")
                if (value != null) {
                    onDataChange(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("firebase", "Failed to read value.", error.toException())
            }
        })
    }

    fun resetPrompts() {
        viewModelScope.launch(Dispatchers.IO) {
            promptDao.clearPrompts()
            promptStore.init()
            _dailyPrompts.value = promptStore.getDailyPrompts()
        }
    }
}

