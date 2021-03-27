import React, {useState, useEffect} from 'react';
import {Link, useHistory} from 'react-router-dom';
import {FiPower, FiEdit, FiTrash2} from 'react-icons/fi';
import api from '../../services/api';

import './styles.css';
import logoImage from '../../assets/logo.svg';

function Books() {
    const [books, setBooks] = useState([]);

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const history = useHistory();

    const headerGet = {
        headers: {
            Authorization: `Bearer ${accessToken}`
        },
        params: {
            page: 0,
            limit: 4,
            direction: 'asc'
        }
    };

    const headerDelete = {
        headers: {
            Authorization: `Bearer ${accessToken}`
        }
    };

    //useEffect é usado para fazer com que os dados sejam carregados na abertura da tela e não após uma ação do usuário.
    useEffect(() => {
        api.get('api/book/v1', headerGet).then(response => {
            setBooks(response.data._embedded.bookVoes);
        });
    });

    //Método para logout
    async function logout() {
        localStorage.clear();
        history.push('/');
    };

    //Método para edição de um livro
    async function editBook(id) {
        try {
            history.push(`book/new/${id}`);
        } catch (err) {
            alert('Erro ao excluir o livro. Tente novamente...');
        }
    }
    
    //Método para exclusão de um livro
    async function deleteBook(id) {
        try {
            //Comando await indica que deve aguardar o retorno da resposta do POST para passar para a próxima linha.
            //Não precisa atribuir o retorno de api.post para uma CONST se o retorno não for usado na página.
            //O React sempre executa uma requisição prévia para verificar se a requisição que será executada na sequência tem permissão na API (React Preflight Request).
            //Assim, a API deve ser configurada para permitir essa requisição prévia.
            await api.delete(`api/book/v1/${id}`, headerDelete);

            //Em caso de sucesso no cadastro, remove o livro excluído do array de livros da página.
            //Com isso, não é necessário consultar o banco de dados novamente.
            setBooks(books.filter(book => book.id !== id));

        } catch (err) {
            alert('Erro ao excluir o livro. Tente novamente...');
        }
    }

    return (
        <div className="book-container">
            <header>
                <img src={logoImage} alt="Almussio" />
                <span>Bem vindo, <strong>{username.toUpperCase()}</strong>!</span>
                <Link className="button" to="book/new/0">Cadastrar novo livro</Link>
                <button type="button" onClick={() => logout()}>
                    <FiPower size={18} color="#251FC5" />
                </button>
            </header>

            <h1>Lista de livros</h1>
            <ul>
                {books.map(book => (
                    //Intl é uma biblioteca de internacionalização do React
                    <li key={book.id}>
                        <strong>Título:</strong>
                        <p>{book.title}</p>
                        <strong>Autor:</strong>
                        <p>{book.author}</p>
                        <strong>Preço:</strong>
                        <p>{Intl.NumberFormat('pt-BR', {style: 'currency', currency: 'BRL'}).format(book.price)}</p>
                        <strong>Data de lançamento:</strong>
                        <p>{Intl.DateTimeFormat('pt-BR').format(new Date(book.launchDate))}</p>

                        <button type="button" onClick={() => editBook(book.id)}>
                            <FiEdit size={20} color="#251FC5" />
                        </button>
                        <button type="button" onClick={() => deleteBook(book.id)}>
                            <FiTrash2 size={20} color="#251FC5" />
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Books;