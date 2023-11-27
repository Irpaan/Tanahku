package com.tanahku.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanahku.R
import com.tanahku.data.TanamanResponse
import com.tanahku.ui.detailtanaman.DetailTanamanActivity

class Adapter(
    private val context: Context,
    private val dataList: ArrayList<TanamanResponse>
): RecyclerView.Adapter<Adapter.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val imgPhoto = view.findViewById<ImageView>(R.id.img_item_photo)
        val tvname = view.findViewById<TextView>(R.id.tv_item_name)
        val tvdesc = view.findViewById<TextView>(R.id.tv_deskripsi)
        val rvTanaman = view.findViewById<CardView>(R.id.card_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_row_tanaman, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvname.text = dataList.get(position).nama_tanaman
        holder.tvdesc.text = dataList.get(position).nama_latin_tanaman
        Glide.with(context)
            .load(dataList.get(position).gambar_tanaman)
            .into(holder.imgPhoto)
        holder.rvTanaman.setOnClickListener{
            val intent = Intent(context, DetailTanamanActivity::class.java)
            intent.putExtra(DetailTanamanActivity.PHOTO_URL, dataList.get(position).gambar_tanaman)
            intent.putExtra(DetailTanamanActivity.NAME, dataList.get(position).nama_tanaman)
            intent.putExtra(DetailTanamanActivity.LATIN, dataList.get(position).nama_latin_tanaman)
            intent.putExtra(DetailTanamanActivity.DESC, dataList.get(position).desc_tanaman)
            intent.putExtra(DetailTanamanActivity.HABITAT, dataList.get(position).habitat_tanaman)
            intent.putExtra(DetailTanamanActivity.KEGUNAAN1, dataList.get(position).kegunaan1)
            intent.putExtra(DetailTanamanActivity.KEGUNAAN2, dataList.get(position).kegunaan2)
            intent.putExtra(DetailTanamanActivity.KEGUNAAN3, dataList.get(position).kegunaan3)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = dataList.size


    fun setData(data: ArrayList<TanamanResponse>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}