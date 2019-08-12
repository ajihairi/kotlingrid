package com.app.testgridview.views.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.widget.ImageView
import com.app.testgridview.ITEM_TYPE_CONTENT
import com.app.testgridview.R
import com.bumptech.glide.Glide


class ImageGridAdapter(var context: Context, var data : ArrayList<String>, var dataType:ArrayList<Int>) : BaseAdapter(){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        var gridView: View
//        if (convertView == null) {
//            gridView = View(context)
//            gridView = inflater.inflate(R.layout.item_image, null)
//            val imgView = gridView.findViewById<View>(R.id.imgView) as ImageView
//            Glide.with(context).load(data[position]).into(imgView)
//        } else {
//            gridView = convertView
//        }
//        return gridView

        val viewHolder: ViewHolderItem
        var conView = convertView
        if (conView == null) {
            // inflate the layout
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            conView = inflater.inflate(com.app.testgridview.R.layout.item_image, parent, false)

            viewHolder = ViewHolderItem()
            viewHolder.imageViewItem = conView.findViewById(R.id.imgView) as ImageView
            // store the holder with the view.
            conView.tag = viewHolder

        } else {
            viewHolder = conView!!.tag as ViewHolderItem
        }

        Glide.with(context).load(data[position]).into(viewHolder.imageViewItem!!)


        return conView!!
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
       return data.size
    }

    fun refreshData(dataImage : ArrayList<String>, dataType : ArrayList<Int>){
        this.data = dataImage
        this.dataType = dataType
        notifyDataSetChanged()
    }

    internal class ViewHolderItem {
        var imageViewItem: ImageView? = null
    }
}