import Login from './Login';
import Register from './Register';

const Auth = () => {
  return (
    <div className="login">
      <Login />
      <div className='separator'></div>
      <Register />
    </div>
  );
};

export default Auth;
