import React, {useState} from 'react';
import {useHistory} from 'react-router-dom';
import api from '../../services/api';

import './styles.css';

import logoImage from '../../assets/logo.svg';
import padlock from '../../assets/padlock.png';

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const history = useHistory();

    async function login(e) {
        //A linha abaixo é um evento que previne o recarregamento da página no submit.
        //Isso é necessário porque em aplicações Single Page Application, não se deve recarregar a página toda.
        e.preventDefault();

        const data = {
            username,
            password,
        };

        try {
            //Comando await indica que deve aguardar o retorno da resposta do POST para passar para a próxima linha.
            const response = await api.post('auth/signin', data);

            //Armazena informações do login em localStorage
            localStorage.setItem('username', username);
            localStorage.setItem('accessToken', response.data.token);

            //Redireciona para página incial após sucesso do login.
            history.push('/books');
        } catch (err) {
            alert("Credenciais inválidas!");
        }
    };

    return (
        <div className="login-container">
            <section className="form">
                <img src={logoImage} alt="Almussio Logo"/>
                <form onSubmit={login}>
                    <h1>Accesse sua conta</h1>
                    <input 
                        placeholder="Usuário"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />
                    <input 
                        type="password" 
                        placeholder="Senha"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />

                    <button className="button" type="submit">Login</button>
                </form>
            </section>

            <img src={padlock} alt="Login"/>
        </div>
    );
}

export default Login;