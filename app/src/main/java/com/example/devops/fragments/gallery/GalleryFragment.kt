package com.example.devops.fragments.gallery

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.devops.R


class GalleryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }


}