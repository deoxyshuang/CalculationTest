package com.bear.calculationtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bear.calculationtest.databinding.FragmentLoseBinding;

public class LoseFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        FragmentLoseBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lose,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button14.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loseFragment_to_titleFragment));
        return binding.getRoot();
    }
}