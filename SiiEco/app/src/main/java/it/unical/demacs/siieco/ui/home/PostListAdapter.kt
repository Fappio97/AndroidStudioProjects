package it.unical.demacs.siieco.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.unical.demacs.siieco.R
import it.unical.demacs.siieco.domain.model.Post
import kotlinx.android.synthetic.main.post_one_image.view.*
import kotlinx.android.synthetic.main.post_only_text.view.*

private const val POST_TYPE_DESC: Int = 0
private const val POST_TYPE_IMAGE: Int = 1

class PostListAdapter(
    var postListItems: List<Post>,
    val clickListener: (Post) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == POST_TYPE_DESC) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.post_only_text, parent, false)
            DescViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.post_one_image, parent, false)
            ImageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == POST_TYPE_DESC) {
            (holder as DescViewHolder).bind(postListItems[position], clickListener)
        } else {
            (holder as ImageViewHolder).bind(postListItems[position], clickListener)
        }
    }

    override fun getItemCount(): Int = postListItems.size

    override fun getItemViewType(position: Int): Int {
        return if(postListItems[position].post_type == 0L) {
            POST_TYPE_DESC
        } else {
            POST_TYPE_IMAGE
        }
    }

    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post, clickListener: (Post) -> Unit) {
            itemView.image_title.text = post.title

            //load image
            Glide.with(itemView.context).load(post.image).into(itemView.imageView)

            //clip image
            itemView.imageView.clipToOutline = true

            itemView.setOnClickListener {
                clickListener(post)
            }
        }
    }

    inner class DescViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post, clickListener: (Post) -> Unit) {
            itemView.desc_post_title.text = post.title
            itemView.desc_post_desc.text = post.desc

            itemView.setOnClickListener {
                clickListener(post)
            }
        }
    }
}