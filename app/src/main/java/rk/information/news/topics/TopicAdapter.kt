package rk.information.news.topics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import rk.information.news.databinding.ItemTopicBinding
import rk.information.news.utils.dataStore.DEFAULT_TOPIC
import rk.information.news.utils.dataStore.DataStoreHelper

class TopicAdapter(
        private val dataStoreHelper: DataStoreHelper,
        private val fragment: Fragment) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    private var topicList: ArrayList<String> = ArrayList()
    private var selectedTopic = ""

    private var previousTopicSelectionHolder: TopicViewHolder? = null

    inner class TopicViewHolder(val itemTopicBinding: ItemTopicBinding) : RecyclerView.ViewHolder(itemTopicBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {

        with(holder) {



            val topic = topicList[position]

            itemTopicBinding.rbTopic.text = topic

            if (topic == selectedTopic) {
                itemTopicBinding.rbTopic.isChecked = true
                previousTopicSelectionHolder = holder
            } else {
                itemTopicBinding.rbTopic.isChecked = false
            }

            if (topic == DEFAULT_TOPIC)
                itemTopicBinding.ivDeleteTopic.visibility = View.GONE
            else
                itemTopicBinding.ivDeleteTopic.visibility = View.VISIBLE

            itemTopicBinding.ivDeleteTopic.setOnClickListener {
                topicList.removeAt(position)
                notifyItemRemoved(position)
                fragment.lifecycleScope.launch {
                    dataStoreHelper.updateTopicList(topicList.toSet())
                    if (selectedTopic == topic)
                        dataStoreHelper.updateSelectedTopic(DEFAULT_TOPIC)
                }
            }

            itemTopicBinding.rbTopic.setOnClickListener {
                selectedTopic = topic
                previousTopicSelectionHolder?.itemTopicBinding?.rbTopic?.isChecked = false
                previousTopicSelectionHolder = holder
                notifyDataSetChanged()
                fragment.lifecycleScope.launch { dataStoreHelper.updateSelectedTopic(selectedTopic) }
            }
        }
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    fun updateList(selectedTopic: String, topicList: Set<String>) {
        this.selectedTopic = selectedTopic
        this.topicList = topicList.toMutableList() as ArrayList<String>
        notifyDataSetChanged()
    }

    public fun getSelectedTopic() = selectedTopic
}