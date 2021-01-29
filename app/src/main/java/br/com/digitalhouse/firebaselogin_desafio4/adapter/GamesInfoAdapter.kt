package br.com.digitalhouse.firebaselogin_desafio4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.firebaselogin_desafio4.R
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo
import com.squareup.picasso.Picasso

class GamesInfoAdapter(
    private val listGames: ArrayList<GamesInfo>,
    val listener: OnGameClick
) : RecyclerView.Adapter<GamesInfoAdapter.GamesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamesViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.item_games_info, parent, false)

        return GamesViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {

        val games = listGames[position]

        holder.title.text = games.titulo
        holder.data.text =  games.data_lancamento

        Picasso.get().load(games.imgURL).into(holder.img)
    }

    override fun getItemCount() = listGames.size

    interface OnGameClick {
        fun onClick(position: Int)
    }


    inner class GamesViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val img: ImageView = view.findViewById(R.id.iv_game)
        val title: TextView = view.findViewById(R.id.tv_title_game)
        val data: TextView = view.findViewById(R.id.tv_year_game)


        init {
            view.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.onClick(position)
            }
        }
    }
}