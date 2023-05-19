package com.example.inventory

import android.content.ClipData
import androidx.lifecycle.*
import com.example.inventory.data.Dao
import com.example.inventory.data.data
import kotlinx.coroutines.launch

class viewmodel(private val dao: Dao):ViewModel() {

    val allitems:LiveData<List<data>> =dao.getitems().asLiveData()

    private fun insertitem(data: data){
        viewModelScope.launch {
            dao.insert(data)
        }
    }
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): data {
        return data(
            name = itemName,
            price = itemPrice.toDouble(),
            stock = itemCount.toInt()
        )
    }
    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertitem(newItem)
    }
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }
    fun reterieveitem(id:Int):LiveData<data>{
        return dao.getitem(id).asLiveData()
    }
    fun updateitem(data: data){
        viewModelScope.launch {
            dao.update(data)
        }
    }
    fun deleteitem(data: data){
        viewModelScope.launch {
            dao.delete(data)
        }
    }

    fun sellitem(data: data){
        if (data.stock>0){
            //The copy() function is provided by default to all the instances of data classes.
            //This function is used to copy an object for changing some of its properties,
            //but keeping the rest of the properties unchanged.
            val newitem= data.copy(stock=data.stock-1)
            updateitem(newitem)
        }
    }
    fun isstockavailable(data: data):Boolean{
        return (data.stock>0)
    }
    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): data {
        return data(
            Id = itemId,
            name = itemName,
            price  = itemPrice.toDouble(),
            stock = itemCount.toInt()
        )
    }
    fun updateitem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ){
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateitem(updatedItem)
    }

}

class viewmodelfactory(private val dao: Dao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(viewmodel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return viewmodel(dao) as T
        }
        throw IllegalArgumentException("unknown viewmodel class")
    }
}