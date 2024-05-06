package com.mantushnikita.movieapp.ui.singup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mantushnikita.mathpro.util.validation.ValidationResult
import com.mantushnikita.movieapp.databinding.FragmentSingupBinding
import com.mantushnikita.movieapp.ui.MainFragment
import com.mantushnikita.movieapp.ui.login.LoginFragment
import com.mantushnikita.movieapp.util.openFragment
import com.mantushnikita.movieapp.util.setValidation
import com.mantushnikita.movieapp.util.validation.validateEmail
import com.mantushnikita.movieapp.util.validation.validateName
import com.mantushnikita.movieapp.util.validation.validatePassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : Fragment() {

    private var binding: FragmentSingupBinding? = null

    private val viewModel: SingUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingupBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {

            singUpUsernameEdit.doAfterTextChanged {
                validate()
            }
            singUpEmailEdit.doAfterTextChanged {
                validate()
            }
            singUpPasswordEdit.doAfterTextChanged {
                validate()
            }
            singUpButton.setOnClickListener {
                if (validate()) {

                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    viewModel.singUp(
                        binding?.singUpEmailEdit?.text.toString(),
                        binding?.singUpPasswordEdit?.text.toString(),
                        binding?.singUpUsernameEdit?.text.toString(),
                        onSuccess = {
                            parentFragmentManager.openFragment(MainFragment())
                        },
                        onError = {exception->
                            Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_LONG).show()

                        }
                    )
                }
            }
            logInTextVIew.setOnClickListener {
                parentFragmentManager.openFragment(LoginFragment())
            }
        }
    }
    private fun validate(): Boolean {
        var inputs = binding?.run {
            listOf(
                singUpUsernameInput to validateName(singUpUsernameInput.editText?.text.toString()),
                singUpEmailInput to validateEmail(singUpEmailInput.editText?.text.toString()),
                singUpPasswordInput to validatePassword(singUpPasswordInput.editText?.text.toString())
            )
        }
        inputs?.forEach { (input, validation) ->
            input.setValidation(validation)
        }
        return inputs?.all { (_, validation) -> validation is ValidationResult.Valid } ?: false
    }
}