package br.com.digitalhouse.firebaselogin_desafio4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.firebaselogin_desafio4.R
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo
import com.bumptech.glide.Glide

class GamesInfoAdapter(private val listGames: ArrayList<GamesInfo>,
                       val listener: View.OnClickListener
): RecyclerView.Adapter<GamesInfoAdapter.GamesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamesInfoAdapter.GamesViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_games_info, parent, false)

        return GamesViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: GamesInfoAdapter.GamesViewHolder, position: Int) {

        val current = listGames[position]

        holder.title.text = current.titulo
        holder.date.text = current.data_lancamento.toString()

        holder.img.setOnClickListener(listener)

        Glide.with(holder.itemView).asBitmap()
            .load(current.img)
            .into(holder.img)

    }

    override fun getItemCount() = listGames.size



    inner class GamesViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        val title: TextView = view.findViewById(R.id.tv_title_game)
        val date: TextView = view.findViewById(R.id.tv_year_game)
        val img: ImageView = view.findViewById(R.id.iv_game)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position){
                listener.onClick(itemView)
            }
        }
    }
}