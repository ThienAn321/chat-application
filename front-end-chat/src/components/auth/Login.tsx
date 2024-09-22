import React, { useState } from 'react';
import { toast } from 'react-toastify';
import { AuthenticationRequest } from '../../models/auth/auth';
import authApi from '../../api/auth/authApi';
import { useDispatch } from 'react-redux';
import { loginRequest, loginSuccess } from '../../redux/slice/authSlice';

const Login = () => {
  const [loginInfo, setLoginInfo] = useState<AuthenticationRequest>({
    email: '',
    password: '',
  });
  const dispatch = useDispatch();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setLoginInfo((prevInfo) => ({
      ...prevInfo,
      [name]: value,
    }));
  };

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const { ok, body } = await authApi.login(loginInfo);
    if (ok && body) {
      dispatch(loginRequest());
      dispatch(
        loginSuccess({ token: body.token, refreshToken: body.refreshToken })
      );
      toast.success('Login successfully');
      setLoginInfo({
        email: '',
        password: '',
      });
    }
  };

  return (
    <div className="item">
      <h2>Welcome back</h2>
      <form onSubmit={handleLogin} autoComplete="off">
        <input
          type="text"
          placeholder="Email"
          name="email"
          value={loginInfo.email}
          onChange={handleInputChange}
        />
        <input
          type="password"
          placeholder="Password"
          name="password"
          value={loginInfo.password}
          onChange={handleInputChange}
        />
        <button>Sign In</button>
      </form>
    </div>
  );
};

export default Login;
