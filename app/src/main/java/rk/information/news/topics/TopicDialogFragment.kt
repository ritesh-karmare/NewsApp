package rk.information.news.topics

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import rk.information.news.R
import rk.information.news.databinding.FragmentTopicDialogBinding
import rk.information.news.utils.dataStore.DEFAULT_TOPIC
import rk.information.news.utils.dataStore.DataStoreHelper

class TopicDialogFragment : DialogFragment() {

    private val TAG = TopicDialogFragment::class.java.name

    private lateinit var topicAdapter: TopicAdapter

    private var topicList = setOf(DEFAULT_TOPIC)

    private val dataStoreHelper by lazy {
        DataStoreHelper(activity!!)
    }

    lateinit var topicDialogBinding: FragmentTopicDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topicDialogBinding = FragmentTopicDialogBinding.bind(view)
        initAdapter()
        initData()
        initListeners()

    }

    private fun initAdapter() {
        topicAdapter = TopicAdapter(dataStoreHelper, this)
        topicDialogBinding.rvTopic.adapter = topicAdapter
    }

    private fun initData() {

        val selectedTopicFlow = dataStoreHelper.getSelectedTopic
        val topicListFlow = dataStoreHelper.getTopicsList

        val flow: Flow<Map<String, Any>> = selectedTopicFlow.zip(topicListFlow) { a, b -> mapOf(Pair("selectedTopic", a), Pair("topicList", b)) }

        flow.asLiveData().observe(this@TopicDialogFragment, {
            topicList = it["topicList"] as Set<String>
            setData(it["selectedTopic"].toString(), it["topicList"] as Set<String>)
        })
    }

    private fun setData(selectedTopic: String, topicList: Set<String>) {

        Log.i(TAG, "setData: $selectedTopic - $topicList")

        if (topicList.isNotEmpty()) {
            topicDialogBinding.btnProceed.visibility = View.VISIBLE
            topicAdapter.updateList(selectedTopic, topicList)

        } else
            topicDialogBinding.btnProceed.visibility = View.GONE
    }

    private fun initListeners() {

        topicDialogBinding.ivClose.setOnClickListener { dismiss() }

        topicDialogBinding.btnProceed.setOnClickListener {
            if (this::topicAdapter.isInitialized) {
                val selectedTopicStr = topicAdapter.getSelectedTopic()
                Toast.makeText(context, selectedTopicStr, Toast.LENGTH_LONG).show()
                dismiss()
            } else
                Toast.makeText(context, "No topic selected.", Toast.LENGTH_LONG).show()
        }

        topicDialogBinding.tvSaveTopic.setOnClickListener {
            val newTopicStr = topicDialogBinding.etAddTopic.text.toString()
            if (!TextUtils.isEmpty(newTopicStr)) {
                if (!topicList.contains(newTopicStr)) {
                    lifecycleScope.launch {
                        val updatedSet = topicList.toMutableSet()
                        updatedSet.add(newTopicStr)
                        dataStoreHelper.updateTopicList(updatedSet)
                        topicDialogBinding.etAddTopic.setText("")
                    }
                } else
                    Toast.makeText(context, "$newTopicStr already exists.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Please enter topic.", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window.attributes = params as WindowManager.LayoutParams
    }
}