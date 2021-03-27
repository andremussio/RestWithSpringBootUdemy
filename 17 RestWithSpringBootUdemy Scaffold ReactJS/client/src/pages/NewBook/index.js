import React, {useState, useEffect} from 'react';
import {useHistory, Link, useParams} from 'react-router-dom';
import {FiArrowLeft} from 'react-icons/fi';
import api from '../../services/api';

import './styles.css';
import logoImage from '../../assets/logo.svg';

function NewBook() {
    const [id, setId] = useState('');
    const [author, setAuthor] = useState('');
    const [title, setTitle] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const history = useHistory();

    //Recebe parâmetro da rota. O nome da CONST deve ser igual ao definido na rota.
    const {bookId} = useParams();

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    //Para bindar variáveis dentro de strings, definir a string entre crases.
    const header = {
        headers: {
            Authorization: `Bearer ${accessToken}`
        }
    };

    //O segundo parâmetro indica que a página ficará monitorando o parâmetro bookId e disparará a requisição somente quando o valor do parâmetro mudar.
    useEffect(() => {
        if (bookId === '0') return;
        else loadBook();
    }, [bookId]);

    //Método que implementa o carregamento dos dados de um livro para edição.
    async function loadBook() {
        try {
            const response = await api.get(`api/book/v1/${bookId}`, header);
            setId(response.data.id);
            setTitle(response.data.title);
            setAuthor(response.data.author);
            setPrice(response.data.price);
            setLaunchDate(response.data.launchDate);
            
        } catch (err) {
            alert('Erro na busca dos dados do livro. Tente novamente...');
            history.push('/books');
        }
    };

    async function createNewBook(e) {
        //A linha abaixo é um evento que previne o recarregamento da página no submit.
        //Isso é necessário porque em aplicações Single Page Application, não se deve recarregar a página toda.
        e.preventDefault();

        const data = {
            author,
            launchDate,
            price,
            title
        };

        try {
            //Comando await indica que deve aguardar o retorno da resposta do POST para passar para a próxima linha.
            //Não precisa atribuir o retorno de api.post para uma CONST se o retorno não for usado na página.
            //O React sempre executa uma requisição prévia para verificar se a requisição que será executada na sequência tem permissão na API (React Preflight Request).
            //Assim, a API deve ser configurada para permitir essa requisição prévia.
            await api.post('api/book/v1', data, header);

            //Em caso de sucesso no cadastro, volta para página de listagem de livros.
            history.push('/books');

        } catch (err) {
            alert("Erro ao cadastrar o livro. Tente novamente...");
        }
    }

    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logoImage} alt="Almussio" />
                    <h1>Cadastrar novo livro</h1>
                    <p>Informe os dados do livro e clique em Adicionar</p>
                    <p>### {bookId} ###</p>
                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#251FC5" />
                        Página inicial
                    </Link>
                </section>
                <form onSubmit={createNewBook}>
                    <input 
                        placeholder="Título" 
                        onChange={e => setTitle(e.target.value)} 
                    />
                    <input 
                        placeholder="Autor" 
                        onChange={e => setAuthor(e.target.value)} 
                    />
                    <input 
                        type="date" 
                        onChange={e => setLaunchDate(e.target.value)} 
                    />
                    <input 
                        placeholder="Preço" 
                        onChange={e => setPrice(e.target.value)} 
                    />

                    <button type="submit" className="button">Adicionar</button>
                </form>
            </div>
        </div>  
    );
}

export default NewBook;