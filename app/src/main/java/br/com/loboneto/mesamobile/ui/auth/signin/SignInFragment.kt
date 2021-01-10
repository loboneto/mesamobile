package br.com.loboneto.mesamobile.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.domain.dto.SignInDTO
import br.com.loboneto.mesamobile.databinding.FragmentSignInBinding
import br.com.loboneto.mesamobile.ui.auth.AuthActivity
import br.com.loboneto.mesamobile.ui.auth.AuthViewModel
import br.com.loboneto.mesamobile.ui.home.HomeActivity

class SignInFragment : Fragment(R.layout.fragment_sign_in), View.OnClickListener {

    private lateinit var binding: FragmentSignInBinding

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignIn.setOnClickListener(this)
        binding.buttonSignUp.setOnClickListener(this)
    }

    private fun checkFields() {
        val email = binding.textInputEmail.text.toString()
        val password = binding.textInputPassword.text.toString()
        if (email.isEmpty()) {
            binding.textLayoutEmail.error = "Campo obrigatório"
            return
        }

        if (password.isEmpty()) {
            binding.textLayoutPassword.error = "Campo obrigatório"
            return
        }

        val signInDTO = SignInDTO(email, password)
        signIn(signInDTO)
    }

    private fun signIn(signInDTO: SignInDTO) {
        viewModel.signIn(signInDTO).observe(this, {
            when (it) {
                is DataState.Loading -> {
                    showProgress()
                }
                is DataState.Success -> {
                    viewModel.saveUserData(it.data.token, signInDTO)
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
            R.id.buttonSignIn -> checkFields()
            R.id.buttonSignUp -> findNavController().navigate(R.id.action_sign_up)
        }
    }
}
