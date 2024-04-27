package com.example.preservenaturetogether.fragments

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.preservenaturetogether.R
import com.example.preservenaturetogether.databinding.FragmentSiteArticleBinding
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_DISTRICT_LIST_SITE_ID
import com.example.preservenaturetogether.utilities.BUNDLE_KEY_SITE_GALLERY_SITE_ID
import com.example.preservenaturetogether.utilities.IS_COORDINATES_NOT_COPIED
import com.example.preservenaturetogether.utilities.IS_DIALOG_NOT_SHOWN_IN_ARTICLE
import com.example.preservenaturetogether.utilities.InjectorUtils
import com.example.preservenaturetogether.utilities.REQUEST_KEY_DISTRICT_LIST_SITE_ID
import com.example.preservenaturetogether.utilities.REQUEST_KEY_SITE_GALLERY_SITE_ID
import com.example.preservenaturetogether.viewmodels.SiteArticleViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class SiteArticleFragment : Fragment() {
    private val viewModel: SiteArticleViewModel by viewModels {
        InjectorUtils.provideSiteArticleViewModelFactory(context = requireContext())
    }

    private lateinit var binding: FragmentSiteArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSiteArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(
            requestKey = REQUEST_KEY_DISTRICT_LIST_SITE_ID
        ) { _, bundle ->
            viewModel.setSite(siteId = bundle.getInt(BUNDLE_KEY_DISTRICT_LIST_SITE_ID))
            updateArticle()
        }
        setFragmentResultListener(
            requestKey = REQUEST_KEY_SITE_GALLERY_SITE_ID
        ) { _, bundle ->
            viewModel.setSite(siteId = bundle.getInt(BUNDLE_KEY_SITE_GALLERY_SITE_ID))
            updateArticle()
        }
    }

    private fun updateArticle() {
        showSiteEcoCondition()
        showSiteName()
        showSiteDescription()
        showSiteSuggestion()
        showSitePhotos()
        showSiteCoordinates()
        showSiteMap()
    }

    @SuppressLint("DiscouragedApi")
    private fun showSiteEcoCondition() {
        binding.siteEcoCondition.setImageResource(
            resources.getIdentifier(
                viewModel.siteIconName, "drawable", requireContext().packageName
            )
        )
        val sharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.app_name), Context.MODE_PRIVATE
        )
        if (sharedPreferences.getBoolean(IS_DIALOG_NOT_SHOWN_IN_ARTICLE, true)) {
            binding.siteEcoConditionHint.visibility = View.VISIBLE
        }
        binding.siteEcoCondition.setOnClickListener {
            EcoConditionDialogFragment(sharedPreferencesKey = IS_DIALOG_NOT_SHOWN_IN_ARTICLE)
                .show(childFragmentManager, EcoConditionDialogFragment.TAG)
            if (sharedPreferences.getBoolean(IS_DIALOG_NOT_SHOWN_IN_ARTICLE, true)) {
                binding.siteEcoConditionHint.visibility = View.GONE
            }
        }
    }

    private fun showSiteName() {
        binding.siteName.text = viewModel.siteName
    }

    private fun showSiteDescription() {
        binding.siteDescription.text = viewModel.siteDescription
    }

    private fun showSiteSuggestion() {
        if (viewModel.siteSuggestion.isNotEmpty()) {
            binding.siteSuggestionLabel.visibility = View.VISIBLE
            binding.siteSuggestion.visibility = View.VISIBLE
            binding.siteSuggestion.text = viewModel.siteSuggestion
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun showSitePhotos() {
        binding.sitePhoto1.setImageResource(
            resources.getIdentifier(
                viewModel.sitePhoto1, "drawable", requireContext().packageName
            )
        )
        if (viewModel.sitePhoto2.isNotEmpty()) {
            binding.sitePhoto2.visibility = View.VISIBLE
            binding.sitePhoto2.setImageResource(
                resources.getIdentifier(
                    viewModel.sitePhoto2, "drawable", requireContext().packageName
                )
            )
        }
    }

    private fun showSiteCoordinates() {
        binding.siteCoordinates.text = viewModel.siteCoordinates
        val sharedPreferences = requireActivity().getSharedPreferences(
            resources.getString(R.string.app_name), Context.MODE_PRIVATE
        )
        if (sharedPreferences.getBoolean(IS_COORDINATES_NOT_COPIED, true)) {
            binding.siteCoordinatesHint.visibility = View.VISIBLE
        }
        binding.siteCoordinates.setOnClickListener {
            requireContext().getSystemService<ClipboardManager>()
                ?.setPrimaryClip(
                    ClipData.newPlainText(
                        "Coordinates", viewModel.siteCoordinates
                    )
                )
            Toast.makeText(
                requireContext(), "Координаты скопированы", Toast.LENGTH_SHORT
            ).show()
            if (sharedPreferences.getBoolean(IS_COORDINATES_NOT_COPIED, true)) {
                binding.siteCoordinatesHint.visibility = View.GONE
                sharedPreferences.edit().putBoolean(IS_COORDINATES_NOT_COPIED, false).apply()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showSiteMap() {
        binding.siteMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.siteMap.setMultiTouchControls(true)
        binding.siteMap.controller.setZoom(15.0)
        val siteMarker = Marker(binding.siteMap)
        siteMarker.position = GeoPoint(viewModel.siteLatitude, viewModel.siteLongitude)
        siteMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        binding.siteMap.controller.setCenter(siteMarker.position)
        binding.siteMap.overlays.add(siteMarker)
        binding.siteMap.invalidate()
        binding.siteMap.setOnTouchListener { v, _ ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    override fun onResume() {
        super.onResume()
        binding.siteMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.siteMap.onPause()
    }
}