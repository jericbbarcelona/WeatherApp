package com.jbarcelona.weatherapp.ui.fragment

import android.content.Intent
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
import com.jbarcelona.weatherapp.databinding.FragmentSignInBinding
import com.jbarcelona.weatherapp.ui.activity.MainActivity
import com.jbarcelona.weatherapp.ui.viewmodel.SignInViewModel
import com.jbarcelona.weatherapp.util.ValidUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentSignInBinding>(
            inflater,
            R.layout.fragment_sign_in,
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
        hideTextAfterTextChanged(binding.etEmailAddress, binding.tvErrorEmailAddress)
        hideTextAfterTextChanged(binding.etPassword, binding.tvErrorPassword)
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmailAddress.text.toString()
            val password = binding.etPassword.text.toString()
            if (validateFields()) {
                setProgressBarVisibility(true)
                viewModel.signIn(email, password)
            }
        }
        binding.tvSignUp.setOnClickListener {
            replaceContainerFragment(SignUpFragment())
        }
    }

    private fun hideTextAfterTextChanged(editText: EditText, tvError: TextView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                tvError.visibility = View.GONE
                binding.tvMainError.visibility = View.GONE
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
        viewModel.signInEvent.observe(viewLifecycleOwner) {
            setProgressBarVisibility(false)
            when (it) {
                is SignInViewModel.AuthResult.Success -> {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                is SignInViewModel.AuthResult.Error -> {
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
            Log.e(SignInFragment::class.simpleName, "fragment=[$fragment]", e)
        }
    }

    private fun validateFields(): Boolean {
        return isValidEmail() and isValidPassword()
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
}