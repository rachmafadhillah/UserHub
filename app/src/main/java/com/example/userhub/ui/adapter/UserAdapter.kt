package com.example.userhub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.userhub.R
import com.example.userhub.data.remote.response.UserResponseItem
import com.example.userhub.databinding.ItemUserBinding

class UserAdapter :
    PagingDataAdapter<UserResponseItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var expandedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            val isExpanded = position == expandedPosition
            holder.bind(user, isExpanded)

            holder.itemView.setOnClickListener {
                val prevExpandedPosition = expandedPosition
                expandedPosition = if (isExpanded) null else position

                prevExpandedPosition?.let { notifyItemChanged(it) }
                notifyItemChanged(position)
            }
        }
    }

    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserResponseItem, isExpanded: Boolean) {
            binding.tvUsername.text = data.name
            binding.tvEmail.text = data.email
            binding.tvCity.text = data.city

            binding.tvPhone.text = "Phone: ${data.phoneNumber}"
            binding.tvAddress.text = "Address: ${data.address}"
            binding.tvGender.text = "Gender: ${if (data.gender == 0) "Male" else "Female"}"

            binding.layoutDetailExpand.visibility = if (isExpanded) View.VISIBLE else View.GONE

            if (isExpanded) {
                binding.show.setImageResource(R.drawable.iconamoon_arrow_up_2)
            } else {
                binding.show.setImageResource(R.drawable.iconamoon_arrow_down_2)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponseItem>() {
            override fun areItemsTheSame(oldItem: UserResponseItem, newItem: UserResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserResponseItem, newItem: UserResponseItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}