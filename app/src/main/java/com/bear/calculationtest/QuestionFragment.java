package com.bear.calculationtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bear.calculationtest.databinding.FragmentQuestionBinding;

public class QuestionFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel myViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), this)).get(MyViewModel.class);
        myViewModel.generator();
        myViewModel.getCurrentScore().setValue(0);
        FragmentQuestionBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        final StringBuilder builder = new StringBuilder();
        View.OnClickListener listener = view -> {
            switch (view.getId()){
                case R.id.button0:
                    builder.append("0");
                    break;
                case R.id.button1:
                    builder.append("1");
                    break;
                case R.id.button2:
                    builder.append("2");
                    break;
                case R.id.button3:
                    builder.append("3");
                    break;
                case R.id.button4:
                    builder.append("4");
                    break;
                case R.id.button5:
                    builder.append("5");
                    break;
                case R.id.button6:
                    builder.append("6");
                    break;
                case R.id.button7:
                    builder.append("7");
                    break;
                case R.id.button8:
                    builder.append("8");
                    break;
                case R.id.button9:
                    builder.append("9");
                    break;
                case R.id.buttonClear:
                    builder.setLength(0); //or = new Builder(); 但效能上較慢
            }
            if (builder.length() == 0) binding.textView9.setText(getString(R.string.input_indicator));
            else binding.textView9.setText(builder.toString());
        };
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);
        binding.buttonSubmit.setOnClickListener(view -> {
            if (builder.length()>0){
                if (Integer.valueOf(builder.toString()).intValue() == myViewModel.getAns().getValue()) {
                    myViewModel.ansCorrect();
                    builder.setLength(0);
                    binding.textView9.setText(getString(R.string.ans_correct_msg));
                } else {
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.winFlag) {
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        myViewModel.winFlag = false;
                        myViewModel.save();
                    } else controller.navigate(R.id.action_questionFragment_to_loseFragment);
                }
            } else {
                Toast.makeText(requireContext(),getString(R.string.alert_msg),Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}