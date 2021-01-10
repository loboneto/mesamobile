package br.com.loboneto.mesamobile.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.domain.dto.SignUpDTO
import br.com.loboneto.mesamobile.databinding.FragmentSignUpBinding
import br.com.loboneto.mesamobile.ui.auth.AuthActivity
import br.com.loboneto.mesamobile.ui.auth.AuthViewModel
import br.com.loboneto.mesamobile.ui.home.HomeActivity

class SignUpFragment : Fragment(R.layout.fragment_sign_up), View.OnClickListener {

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignUp.setOnClickListener(this)
    }

    private fun checkFields() {
        val name = binding.textInputName.text.toString()
        val email = binding.textInputEmail.text.toString()
        val password = binding.textInputPassword.text.toString()
        val confirmPassword = binding.textInputConfirmPassword.text.toString()

        if (name.isEmpty()) {
            binding.textLayoutName.error = "Campo obrigatório"
            return
        }

        if (email.isEmpty()) {
            binding.textLayoutEmail.error = "Campo obrigatório"
            return
        }

        if (password.isEmpty()) {
            binding.textLayoutPassword.error = "Campo obrigatório"
            return
        }

        if (confirmPassword.isEmpty()) {
            binding.textLayoutConfirmPassword.error = "Campo obrigatório"
            return
        }

        if (password != confirmPassword) {
            binding.textLayoutPassword.error = "As senhas informadas são diferentes"
            binding.textLayoutConfirmPassword.error = "As senhas informadas são diferentes"
            return
        }

        val signUpDTO = SignUpDTO(name, email, password)
        signIn(signUpDTO)
    }

    private fun signIn(signUpDTO: SignUpDTO) {
        viewModel.signUp(signUpDTO).observe(this, {
            when (it) {
                is DataState.Loading -> {
                    showProgress()
                }
                is DataState.Success -> {
                    viewModel.saveUserData(it.data.token, signUpDTO)
                    openHome()
                }
                is DataState.Error -> {
                    hideProgress()
                    showMessage(it.message)
                }
                is DataState.Failure -> {
                    hideProgress()
                    showMessage("Falha ao realizar login")
                }
            }
        })
    }

    private fun showProgress() {
        (activity as AuthActivity).showProgress()
    }

    private fun hideProgress() {
        (activity as AuthActivity).hideProgress()
    }

    private fun showMessage(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    private fun openHome() {
        startActivity(Intent(requireContext(), HomeActivity::class.java))
        activity?.finish()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonSignUp -> checkFields()
        }
    }
}
