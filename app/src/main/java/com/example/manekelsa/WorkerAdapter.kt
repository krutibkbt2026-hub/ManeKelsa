package com.example.manekelsa

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WorkerAdapter(
    private val workerList: ArrayList<Worker>
) : RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    class WorkerViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val imageWorker: ImageView =
            itemView.findViewById(R.id.imageWorkerCard)

        val textName: TextView =
            itemView.findViewById(R.id.textName)

        val textWork: TextView =
            itemView.findViewById(R.id.textWork)

        val textArea: TextView =
            itemView.findViewById(R.id.textArea)

        val textAvailability: TextView =
            itemView.findViewById(R.id.textAvailability)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkerViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_worker,
                parent,
                false
            )

        return WorkerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: WorkerViewHolder,
        position: Int
    ) {

        val worker = workerList[position]

        val currentLanguage =
            holder.itemView.context
                .resources
                .configuration
                .locales[0]
                .language

        // Name Translation
        holder.textName.text =

            when(worker.name) {

                "Bhanu" -> {
                    if (currentLanguage == "kn") {
                        "ಭಾನು"
                    } else {
                        "Bhanu"
                    }
                }

                "Shreya" -> {
                    if (currentLanguage == "kn") {
                        "ಶ್ರೇಯಾ"
                    } else {
                        "Shreya"
                    }
                }

                "Bhagya" -> {
                    if (currentLanguage == "kn") {
                        "ಭಾಗ್ಯ"
                    } else {
                        "Bhagya"
                    }
                }

                else -> worker.name
            }

        // Work Translation
        holder.textWork.text =

            when(worker.work) {

                "Cooking" ->
                    holder.itemView.context.getString(
                        R.string.cooking
                    )

                "Cleaning" ->
                    holder.itemView.context.getString(
                        R.string.cleaning
                    )

                "Gardening" ->
                    holder.itemView.context.getString(
                        R.string.gardening
                    )

                else -> worker.work
            }

        // Area Translation
        holder.textArea.text =

            when(worker.area) {

                "BTM" -> {
                    if (currentLanguage == "kn") {
                        "ಬಿಟಿಎಂ"
                    } else {
                        "BTM"
                    }
                }

                "RR Nagar" -> {
                    if (currentLanguage == "kn") {
                        "ಆರ್ ಆರ್ ನಗರ"
                    } else {
                        "RR Nagar"
                    }
                }

                else -> worker.area
            }

        // Availability
        if (worker.available == true) {

            holder.textAvailability.text =
                "🟢 " +
                        holder.itemView.context.getString(
                            R.string.online
                        )

        } else {

            holder.textAvailability.text =
                "🔴 " +
                        holder.itemView.context.getString(
                            R.string.busy
                        )
        }

        // Load Image
        Glide.with(holder.itemView.context)
            .load(worker.imageUrl)
            .placeholder(
                android.R.drawable.ic_menu_camera
            )
            .into(holder.imageWorker)

        // Open Details Screen
        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                DetailsActivity::class.java
            )

            // PASS EVERYTHING
            intent.putExtra(
                "workerId",
                worker.workerId
            )

            intent.putExtra(
                "name",
                worker.name
            )

            intent.putExtra(
                "work",
                worker.work
            )

            intent.putExtra(
                "experience",
                worker.experience
            )

            intent.putExtra(
                "phone",
                worker.phone
            )

            intent.putExtra(
                "area",
                worker.area
            )

            intent.putExtra(
                "available",
                worker.available ?: true
            )

            intent.putExtra(
                "imageUrl",
                worker.imageUrl
            )

            holder.itemView.context
                .startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return workerList.size
    }
}