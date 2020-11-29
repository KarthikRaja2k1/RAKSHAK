package com.save.rakshak.rakshakuser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdatepassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdatepassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    View v;
  public  EditText et_Aadhar,et_password;
    Intent in;
    Button btn_login;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_login, container, false);
               et_Aadhar =(EditText) v.findViewById(R.id.LAadhar);
        et_password =(EditText) v.findViewById(R.id.Lpassword);
        btn_login = (Button) v.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        return v;}




    @Override
    public void onClick(View view) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final String Aadhar = et_Aadhar.getText().toString().trim();
         final String password = et_password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if ( response.contains("Logging in")) {
                            in = new Intent(getActivity(),second_main.class);
                            TextView Text;
                            Text = (TextView) v.findViewById(R.id.LAadhar);
                            String message = Text.getText().toString();
                            in.putExtra("EXTRA_MESSAGE", message);
                            startActivity(in);

                        }


                          //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

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
                params.put(Constants.KEY_Aadhar,Aadhar);
                  params.put(Constants.KEY_PASSWORD, password);
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
    }}