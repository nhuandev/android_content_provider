package com.example.appcontentprovider.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontentprovider.R
import com.example.appcontentprovider.databinding.ItemUserBinding
import com.example.appcontentprovider.utils.ConvertData

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
                tvCityName.text = user.cityName
                if (user.avatar != null) {
                    imgUser.setImageBitmap(ConvertData(root.context).byteArrayToBitmap(user.avatar!!))
                } else {
                    imgUser.setImageResource(R.drawable.ic_launcher_foreground)
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
