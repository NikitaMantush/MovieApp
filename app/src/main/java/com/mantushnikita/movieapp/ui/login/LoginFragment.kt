package com.mantushnikita.movieapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mantushnikita.mathpro.util.validation.ValidationResult
import com.mantushnikita.movieapp.databinding.FragmentLoginBinding
import com.mantushnikita.movieapp.ui.MainFragment
import com.mantushnikita.movieapp.ui.singup.SingUpFragment
import com.mantushnikita.movieapp.util.openFragment
import com.mantushnikita.movieapp.util.setValidation
import com.mantushnikita.movieapp.util.validation.validateEmptyField
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            singInEmailEdit.doAfterTextChanged {
                validate()
            }
            singInPasswordEdit.doAfterTextChanged {
                validate()
            }
            logInButton.setOnClickListener {
                if (validate()) {
                    viewModel.login(
                        binding?.singInEmailEdit?.text.toString(),
                        binding?.singInPasswordEdit?.text.toString(),
                        onSuccess = {
                            parentFragmentManager.openFragment(MainFragment())
                        },
                        onError = { exception ->
                            Toast.makeText(requireContext(),
                                exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
            singUpTextVIew.setOnClickListener {
                parentFragmentManager.openFragment(SingUpFragment())
            }
        }
    }

    private fun validate(): Boolean {
        val inputs = binding?.run {
            listOf(
                singInEmailInput to validateEmptyField(singInEmailInput.editText?.text.toString()),
                singInPasswordInput to validateEmptyField(singInPasswordInput.editText?.text.toString())
            )
        }
        inputs?.forEach { (input, validation) ->
            input.setValidation(validation)
        }
        return inputs?.all { (_, validation) -> validation is ValidationResult.Valid } ?: false
    }
}