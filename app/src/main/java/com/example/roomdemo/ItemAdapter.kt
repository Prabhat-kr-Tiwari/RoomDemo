package com.example.roomdemo

import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ItemRowBinding

class ItemAdapter(private val items:ArrayList<EmployeeEntity>,
                  private val updateListener: (id:Int)->Unit,
                  private val deleteListener: (id:Int)->Unit


):RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root){

        val llMain=binding.llMain
        val tvName=binding.tvName
        val tvEMail=binding.tvEmail
        val ivEdit=binding.ivEdit
        val ivDelete=binding.ivDelete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false ))
    }

    override fun getItemCount(): Int {

        return items.size
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
       val context=holder.itemView.context

        val item=items[position]

        holder.tvName.text=item.name
        holder.tvEMail.text=item.email
        if (position%2==0){
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.lightGray))
        }else{
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))

        }
        holder.ivEdit.setOnClickListener { updateListener.invoke(item.id)
             }
        holder.ivDelete.setOnClickListener { deleteListener.invoke(item.id)
         }
    }
}