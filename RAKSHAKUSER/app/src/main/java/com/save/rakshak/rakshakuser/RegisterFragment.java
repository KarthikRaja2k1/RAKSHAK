package com.save.rakshak.rakshakuser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;



import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    View v;
    EditText et_username,et_email,et_emailver,et_password,et_passwordver,et_Aadhar;
    TextView btn_gota_login;
    Button btn_register;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_register, container, false);
        et_username =(EditText) v.findViewById(R.id.et_name);
        et_email =(EditText) v.findViewById(R.id.et_email);
        et_emailver =(EditText) v.findViewById(R.id.et_email_ver);
        et_password =(EditText) v.findViewById(R.id.et_password);
        et_Aadhar =(EditText) v.findViewById(R.id.et_Aadhar);
        et_passwordver =(EditText) v.findViewById(R.id.et_password_ver);
        btn_gota_login =(TextView) v.findViewById(R.id.btn_gota_login);
        btn_register = (Button) v.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_gota_login.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch(v.getId()){
            case R.id.btn_register:
                final String name = et_username.getText().toString().trim();
                final String Aadhar = et_Aadhar.getText().toString().trim();
                final String email = et_email.getText().toString().trim();
                String emailver = et_emailver.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                String passwordver = et_passwordver.getText().toString().trim();

                if(name.equals("")|| email.equals("")||emailver.equals("")||password.equals("")||passwordver.equals(""))
                    Toast.makeText(getActivity(),"Please fill in all fields!!",Toast.LENGTH_SHORT).show();
                else if(!email.equals(emailver)&& !password.equals(passwordver))
                    Toast.makeText(getActivity(),"Emails and Password don't match!",Toast.LENGTH_SHORT).show();
                else if(!email.equals(emailver))
                    Toast.makeText(getActivity(),"Emails don't match!",Toast.LENGTH_SHORT).show();
                else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()== false)
                    Toast.makeText(getActivity(),"Email id invalid",Toast.LENGTH_SHORT).show();
                else if(!password.equals(passwordver))
                    Toast.makeText(getActivity(),"Passwords don't match!",Toast.LENGTH_SHORT).show();
                    //else if(!password.matches(regex))
                    //  Toast.makeText(getActivity(),"Passwords invalid",Toast.LENGTH_SHORT).show();

                else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {if (response.contains("Please check your e-mail") || response.contains("Email already exists")) {

                                            final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                      fragmentManager.beginTransaction().replace(R.id.content,new LoginFragment()).commit();

                                    }
                                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (error instanceof TimeoutError) {
                                Toast.makeText(getActivity(), "Timeout Error!!", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NoConnectionError) {
                                Toast.makeText(getActivity(), "no connection Error!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getActivity(), "Authentication Error!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof NetworkError) {
                                Toast.makeText(getActivity(), "Network Error!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof ServerError) {
                                Toast.makeText(getActivity(), "Server Error!!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof ParseError) {
                                Toast.makeText(getActivity(), "JSON parse Error!!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams()   {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(Constants.KEY_NAME, name);
                            params.put(Constants.KEY_EMAIL, email);
                            params.put(Constants.KEY_PASSWORD, password);
                            params.put(Constants.KEY_Aadhar, Aadhar);
                            return params;
                        }
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("User-Agent", "RAKSHAKUSER");
                            return headers;
                        }

                    };
                    MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                }
                break;
            case R.id.btn_gota_login:

                fragmentManager.beginTransaction().replace(R.id.content, new LoginFragment()).commit();
                break;
            default:
                break;

        }


    }

}
