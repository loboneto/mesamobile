package br.com.loboneto.mesamobile.ui.home.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsFilter
import br.com.loboneto.mesamobile.databinding.BottomSheetNewsFilterBinding
import br.com.loboneto.mesamobile.util.MaskUtil
import br.com.loboneto.mesamobile.util.serverFormat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewsFilterBottomSheet : BottomSheetDialogFragment(),
    View.OnClickListener {

    private lateinit var binding: BottomSheetNewsFilterBinding

    private lateinit var listener: OnResultListener

    private lateinit var filter: NewsFilter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.also { activityListener ->
            if (activityListener !is OnResultListener) throw RuntimeException(ATTACH_EXCEPTION)
            listener = activityListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetNewsFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilter()
        setListener()
    }

    private fun getFilter() {
        filter = arguments?.getSerializable("filter") as NewsFilter
    }

    private fun setListener() {
        binding.textInputDate.addTextChangedListener(MaskUtil.mask(binding.textInputDate))
        binding.buttonFilter.setOnClickListener(this)
        binding.buttonClearFilter.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonFilter -> checkFilter()
            R.id.buttonClearFilter -> clearFilter()
        }
    }

    private fun clearFilter() {
        filter = NewsFilter()
        saveFilter()
    }

    private fun checkFilter() {
        val date = binding.textInputDate.text.toString()
        if (date.isEmpty() || date.length < 10) {
            Toast.makeText(context, "Data vazia ou incompleta", Toast.LENGTH_LONG).show()
        } else {
            filter.dateFilter(date.serverFormat())
            saveFilter()
        }
    }

    private fun saveFilter() {
        listener.setResultPass(filter)
        dismiss()
    }

    interface OnResultListener {
        fun setResultPass(filter: NewsFilter)
    }

    companion object {
        const val ATTACH_EXCEPTION = "A activity deve implementar OnResultListener"
    }
}
