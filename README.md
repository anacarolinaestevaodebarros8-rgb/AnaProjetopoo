# 📚 Folhear - API REST de Troca e Compartilhamento de Livros

![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Supabase](https://img.shields.io/badge/Supabase-PostgreSQL-orange)

Plataforma para **trocar, vender e compartilhar livros** com comunidade local, gerenciamento de lista de desejos, avaliações e pontos de encontro.

---

## 🎯 Funcionalidades Principais

### 📖 Gestão de Livros
- ✅ Publicar livros para venda ou troca
- ✅ Estado de conservação (Ótimo, Bom, Regular, Usado)
- ✅ Múltiplos gêneros e categorias
- ✅ Fotos e descrição detalhada
- ✅ Lista de desejo pessoal

### 🔄 Sistema de Troca
- ✅ Proposta de troca entre usuários
- ✅ Fluxo: Proposta → Aceita/Recusa → Contraproposta → Concluída
- ✅ Rastreamento de transações
- ✅ Histórico completo

### 💰 Marketplace
- ✅ Compra e venda de livros
- ✅ Status de transação (Pendente, Pago, Enviado, Entregue)
- ✅ Integração com dados de frete (Remessa)
- ✅ Suporte a disputa e reembolso

### 👥 Comunidade
- ✅ Avaliações de livros, usuários e pontos de encontro
- ✅ Chat entre usuários
- ✅ Clube do livro com membros e eventos
- ✅ Notificações em tempo real

### 📍 Pontos de Encontro
- ✅ Biblioteca, livraria, café
- ✅ Localização geográfica (Haversine para proximidade)
- ✅ Avaliações de locais

### 🖊️ Obras Autorais
- ✅ Autores publicam suas obras na plataforma
- ✅ Capítulos numerados e organizados
- ✅ Público leitor/crítico da comunidade

---

## 🏗️ Arquitetura

### Dois Pontos de Acesso ao Supabase

```
┌─────────────────────────────────────────┐
│          Spring Boot (Java)             │
└───────────┬─────────────────────────────┘
            │
      ┌─────┴──────┐
      │            │
   JDBC (1)    HTTPS (2)
   ─────────   ─────────
   
1️⃣ JDBC (Port 5432)
   └─ JPA/Hibernate (operações transacionais)
   └─ Pool HikariCP
   └─ Para: CRUD, Transações, Batch
   
2️⃣ HTTPS (PostgREST)
   └─ WebClient (REST + RPC)
   └─ Para: Buscas, Filtros, Full-text
   └─ RPC Functions no PostgreSQL
```

**📊 Ver também:** [`ARQUITETURA_SUPABASE.md`](ARQUITETURA_SUPABASE.md) e [`GUIA_PRATICO_JDBC_POSTGREST.md`](GUIA_PRATICO_JDBC_POSTGREST.md)

---

## 📦 Estrutura do Projeto

```
folhear/
├── pom.xml                                    # Maven: Spring Boot, JPA, PostgreSQL, JWT
├── README.md                                  # Este arquivo
├── ARQUITETURA_SUPABASE.md                   # Detalhes da arquitetura de dois acessos
├── GUIA_PRATICO_JDBC_POSTGREST.md            # Exemplos práticos e benchmarks
│
└── src/main/
    ├── resources/
    │   └── application.properties             # Conexão Supabase, JPA, JWT, servidor
    │
    └── java/com/folhear/
        │
        ├── FolhearApplication.java            # Entry point (@SpringBootApplication)
        │
        ├── config/                            # Configurações da aplicação
        │   ├── JpaConfig.java                 # EntityManagerFactory, Hibernate
        │   ├── SecurityConfig.java            # Filtro JWT Supabase
        │   └── SupabaseClientConfig.java      # WebClient → PostgREST
        │
        ├── entity/                            # Modelos de dados (@Entity)
        │   ├── Usuario.java                   # Usuário (LEITOR, AUTOR, AMBOS)
        │   ├── Livro.java                     # Anúncio de livro (VENDA/TROCA)
        │   ├── ObraAutoral.java               # Obra escrita por autor plataforma
        │   ├── Capitulo.java                  # Capítulo de ObraAutoral
        │   ├── Troca.java                     # Proposta de troca
        │   ├── TrocaItem.java                 # Item individual da troca
        │   ├── Transacao.java                 # Compra/venda com rastreamento
        │   ├── PontoEncontro.java             # Local físico para encontros
        │   ├── Clube.java                     # Clube do livro
        │   ├── ListaDesejo.java               # Lista de desejos (1:1 Usuário)
        │   ├── ListaDesejoItem.java           # Item da lista
        │   ├── Avaliacao.java                 # Avaliações (livro/usuário/ponto)
        │   ├── Notificacao.java               # Sistema de notificações
        │   ├── ChatMensagem.java              # Mensagens entre usuários
        │   ├── Remessa.java                   # Dados de frete/rastreamento
        │   │
        │   └── enums/                         # Enumerações
        │       ├── TipoUsuario.java           # LEITOR, AUTOR, AMBOS
        │       ├── TipoAnuncio.java           # VENDA, TROCA, AMBOS
        │       ├── EstadoConservacao.java     # OTIMO, BOM, REGULAR, USADO
        │       ├── StatusTransacao.java       # PENDENTE, PAGO, ENVIADO, ENTREGUE, ...
        │       ├── StatusTroca.java           # PROPOSTA, ACEITA, RECUSADA, ...
        │       ├── TipoAvaliacao.java         # LIVRO, USUARIO, PONTO_ENCONTRO
        │       ├── TipoNotificacao.java       # TROCA, VENDA, CHAT, SISTEMA, CLUBE
        │       ├── TipoPonto.java             # BIBLIOTECA, LIVRARIA, CAFE, OUTRO
        │       ├── TipoObra.java              # ROMANCE, CONTO, POESIA, CRONICA
        │       └── GeneroLivro.java           # FICCAO, NAO_FICCAO, FANTASIA, ...
        │
        ├── repository/                        # Data Access Layer (JpaRepository)
        │   └── Repositories.java              # Todos os repos em um arquivo
        │       ├── UsuarioRepository
        │       ├── LivroRepository
        │       ├── TrocaRepository
        │       ├── TransacaoRepository
        │       ├── PontoEncontroRepository
        │       ├── AvaliacaoRepository
        │       └── ... (mais 8+)
        │
        └── service/                           # Lógica de negócio
            ├── LivroService.java              # CRUD + RPC indexação
            └── TrocaService.java              # Fluxo completo de troca
```

---

## 🚀 Quick Start

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Supabase Account ([supabase.com](https://supabase.com))

### 1️⃣ Clone e Configure

```bash
git clone https://github.com/seu-usuario/AnaProjetopoo.git
cd AnaProjetopoo

# Copie o template de variáveis
cp .env.example .env
```

### 2️⃣ Configure Variáveis de Ambiente

```bash
# .env ou export
export SUPABASE_DB_PASSWORD="sua_senha_db"
export SUPABASE_ANON_KEY="eyJ..."
export SUPABASE_SERVICE_KEY="eyJ..."
export SUPABASE_JWT_SECRET="seu_jwt_secret"
```

**Obtém estes valores em:** Supabase → Settings → API

### 3️⃣ Execute o Projeto

```bash
# Compile
mvn clean install

# Inicie a aplicação
mvn spring-boot:run

# Ou com seu IDE (F5 em VS Code com Java debugger)
```

**URL da API:** `http://localhost:8080`

---

## 🔌 API Endpoints (Exemplos)

### Livros
```bash
# Listar livros disponíveis
GET /api/livros?genero=ROMANCE&estado=OTIMO

# Criar novo anúncio
POST /api/livros
Content-Type: application/json
{
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "genero": "NAO_FICCAO",
  "tipoAnuncio": "VENDA",
  "preco": 49.90
}

# Detalhes do livro
GET /api/livros/{id}

# Atualizar anúncio
PUT /api/livros/{id}

# Remover
DELETE /api/livros/{id}
```

### Trocar
```bash
# Propor troca
POST /api/trocas
{
  "proponenteId": 1,
  "receptorId": 2,
  "livroOfertaId": 10,
  "livroSolicitacaoId": 20
}

# Aceitar proposta
PATCH /api/trocas/{id}/aceitar

# Recusar proposta
PATCH /api/trocas/{id}/recusar
```

---

## 📊 Banco de Dados

### Tabelas Principais
- `usuario` - Usuários cadastrados
- `livro` - Anúncios de livros
- `troca` - Propostas de troca
- `troca_item` - Itens de cada troca
- `transacao` - Vendas e compras
- `ponto_encontro` - Locais de encontro
- `clube` - Clube do livro
- `avaliacao` - Ratings
- `notificacao` - Notificações
- `chat_mensagem` - Mensagens entre usuários
- `remessa` - Frete e rastreamento

### Diagrama ER (Simplificado)

```
┌─────────────┐
│   Usuario   │
└──────┬──────┘
       │ 1
       ├─────────→ ListaDesejo
       │ 1         └─────────→ ListaDesejoItem
       │
       ├─────────→ Livro
       │           └─────────→ Avaliacao
       │
       ├─────────→ Troca ←─────────┐
       │           └─────────→ TrocaItem ─→ Livro
       │
       ├─────────→ Transacao ─────→ Remessa
       │
       ├─────────→ ObraAutoral ───→ Capitulo
       │
       └─────────→ ChatMensagem ←─┐
                                    └─ Usuario (remetente/destinatário)
```

---

## 🔐 Segurança

### Autenticação
- JWT token via Supabase Auth
- Armazenado em `Authorization` header
- Validado por `SecurityConfig.java` (JWT Filter)

### Autorização (RLS)
- Row Level Security no PostgreSQL
- anon key: respeita RLS
- service key: ignora RLS (admin only)

### Variáveis de Ambiente
```bash
SUPABASE_DB_PASSWORD     # Senha do banco
SUPABASE_ANON_KEY        # Chave pública (client-side)
SUPABASE_SERVICE_KEY     # Chave privada (server-side)
SUPABASE_JWT_SECRET      # Secret para validar JWT
```

---

## 📚 Tecnologias

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Spring Boot | 3.x | Framework principal |
| JPA/Hibernate | 6.x | ORM, mapeamento de entidades |
| PostgreSQL | 15+ | Banco de dados (via Supabase) |
| JWT | 1.x | Autenticação |
| Lombok | 1.18+ | Reducir boilerplate (@Data, @Getter) |
| Maven | 3.8+ | Build tool |

---

## 🧪 Testes

```bash
# Executar testes
mvn test

# Com coverage
mvn test jacoco:report

# Teste de integração
mvn verify
```

---

## 📖 Documentação Detalhada

- **[ARQUITETURA_SUPABASE.md](ARQUITETURA_SUPABASE.md)** - Dois pontos de acesso, RLS, segurança
- **[GUIA_PRATICO_JDBC_POSTGREST.md](GUIA_PRATICO_JDBC_POSTGREST.md)** - Exemplos de código, benchmarks, quando usar cada um

---

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 📝 License

Distribuído sob a license MIT. Ver `LICENSE` para mais detalhes.

---

## 👥 Autores

- **Ana Carolina Estevão de Barros** - [@anacarolinaestevaodebarros8-rgb](https://github.com/anacarolinaestevaodebarros8-rgb)

---

## 📞 Suporte

Para dúvidas ou sugestões:
- Abra uma [Issue](https://github.com/seu-usuario/AnaProjetopoo/issues)
- Entre em contato via email

---

## 🙏 Agradecimentos

- Supabase pelo PostgreSQL gerenciado
- Spring Boot pelo framework robusto
- Comunidade open source
