package com.example.client.ui.user

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.databinding.ItemUserBinding
import com.example.client.models.UserDB

class UserAdapter(
    private var listUser: List<UserDB>,
    private val onItemClick: (UserDB) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDB) {
            binding.apply {
                tvUserName.text = user.userName
                tvUserAge.text = user.userAge.toString()
                user.avatar?.let {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    imgUser.setImageBitmap(bitmap)
                } ?: run {
                    imgUser.setImageResource(android.R.drawable.ic_menu_gallery)
                }

                root.setOnClickListener {
                    onItemClick(user)
                }
            }
        }
    }

    fun updateData(newList: List<UserDB>) {
        listUser = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}
