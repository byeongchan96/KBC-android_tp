package com.example.intravel.adapter

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.intravel.PhotoFullActivity
import com.example.intravel.client.Client
import com.example.intravel.data.PhotoData
import com.example.intravel.databinding.CustomPhotoBinding
import com.example.intravel.databinding.ItemGalleryBinding
import retrofit2.Call
import retrofit2.Response

class GalleryAdapter(var photoList: MutableList<PhotoData>): RecyclerView.Adapter<GalleryAdapter.Holder>() {

  private val REQUEST_DELETE_PHOTO = 100
  var position1 = 0

  fun removeData(position: Int){
    photoList.removeAt(position)
    notifyDataSetChanged()
  }

  inner class Holder(val binding: ItemGalleryBinding): RecyclerView.ViewHolder(binding.root) {
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.Holder {
    return Holder(ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: GalleryAdapter.Holder, position: Int) {
    val photo = photoList[position]

    val url = "http://10.100.105.208:8811/photos/"

    Glide.with(holder.binding.root.context)
      .load(url + photo.fileName)
      .into(holder.binding.galleryIv)

    holder.itemView.setOnClickListener {
      var dialogPhoto = CustomPhotoBinding.inflate(LayoutInflater.from(it.context))

      Log.d("clickclick","clickclick")

      Glide.with(it.context)
        .load(url + photo.fileName)
        .into(dialogPhoto.photo)

      AlertDialog.Builder(it.context).run{
//        setTitle("사진 크게보기!")
        setView(dialogPhoto.root)

        setPositiveButton("삭제하기",object: DialogInterface.OnClickListener{
          override fun onClick(p0: DialogInterface?, p1: Int) {
            Client.photoRetrofit.deletePhoto(photo.photoId).enqueue(object:retrofit2.Callback<Void>{
              override fun onResponse(call: Call<Void>, response: Response<Void>) {
                removeData(holder.adapterPosition)
              }
              override fun onFailure(call: Call<Void>, t: Throwable) {
                TODO("Not yet implemented")
              }
            })
          }
        })
        setNegativeButton("닫기",null)
        show()
      }
//      holder.binding.galleryIv.setOnClickListener {
//        val intent = Intent(holder.binding.root.context, PhotoFullActivity::class.java)
//        intent.putExtra("url", url)
//        intent.putExtra("fileName", photo.fileName)
//        intent.putExtra("photoId", photo.photoId)
//        intent.putExtra("position", position)
//        position1 = position
//        (holder.binding.root.context as Activity).startActivityForResult(intent, REQUEST_DELETE_PHOTO)
////        removeData(holder.adapterPosition)
//
//      }
    }
  }

  override fun getItemCount(): Int {
    return photoList.size
  }

  val removePhoto = {
    removeData(position1)
  }
}