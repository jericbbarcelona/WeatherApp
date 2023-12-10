package com.jbarcelona.weatherapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.databinding.FragmentSignUpBinding
import com.jbarcelona.weatherapp.ui.viewmodel.SignUpViewModel
import com.jbarcelona.weatherapp.util.ValidUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
            this.viewModel = viewModel
        }
        setupObservers()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        hideTextAfterTextChanged(binding.etName, binding.tvErrorName)
        hideTextAfterTextChanged(binding.etEmailAddress, binding.tvErrorEmailAddress)
        hideTextAfterTextChanged(binding.etPassword, binding.tvErrorPassword)
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailAddress.text.toString()
            val password = binding.etPassword.text.toString()
            if (validateFields()) {
                setProgressBarVisibility(true)
                viewModel.signUp(email, password)
            }
        }
        binding.tvSignIn.setOnClickListener {
            replaceContainerFragment(SignInFragment())
        }
    }

    private fun hideTextAfterTextChanged(editText: EditText, tvError: TextView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                tvError.visibility = View.GONE
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setProgressBarVisibility(visible: Boolean) {
        if (visible) {
            binding.rlProgressbar.visibility = View.VISIBLE
        } else {
            binding.rlProgressbar.visibility = View.GONE
        }
    }

    private fun setupObservers() {
        viewModel.signUpEvent.observe(viewLifecycleOwner) {
            setProgressBarVisibility(false)
            when (it) {
                is SignUpViewModel.AuthResult.Success -> {
                    Toast.makeText(activity, context?.getString(R.string.sign_up_account_created_successfully_message), Toast.LENGTH_LONG).show()
                    replaceContainerFragment(SignInFragment())
                }
                is SignUpViewModel.AuthResult.Error -> {
                    binding.tvMainError.visibility = View.VISIBLE
                    binding.tvMainError.text = it.message
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun replaceContainerFragment(fragment: Fragment) {
        try {
            activity?.apply {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(R.anim.slide_bottom, 0)
                fragmentTransaction.replace(R.id.fl_container, fragment).commit()
            }
        } catch (e: Exception) {
            Log.e(SignUpFragment::class.simpleName, "fragment=[$fragment]", e)
        }
    }

    private fun validateFields(): Boolean {
        return isValidEmail() and isValidPassword() and isValidName()
    }

    private fun isValidEmail(): Boolean {
        val email = binding.etEmailAddress.text.toString().trim()
        if (!ValidUtil.isValidEmail(email)) {
            binding.tvErrorEmailAddress.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun isValidPassword(): Boolean {
        val password = binding.etPassword.text.toString().trim()
        if (!ValidUtil.isValidPassword(password)) {
            binding.tvErrorPassword.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun isValidName(): Boolean {
        val name = binding.etName.text.toString().trim()
        if (ValidUtil.isEmpty(name)) {
            binding.tvErrorName.visibility = View.VISIBLE
            return false
        }
        return true
    }
}