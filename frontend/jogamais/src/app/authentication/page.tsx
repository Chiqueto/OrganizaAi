"use client"
import {
    Card,
    CardAction,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

import LoginForm from "./components/loginForm"
import { useState } from "react"
import { Button } from "@/components/ui/button"
import RegisterForm from "./components/registerForm"


const AuthenticationPage = () => {

    const [isLogin, setIsLogin] = useState(true)

    const handleChange = () => {
        setIsLogin(!isLogin)
    }

    return (
        <section className="h-dvh flex items-center justify-center">
            <Card className="md:max-w-sm max-w-10/12 w-full">
                <CardHeader>
                    <CardTitle>Login</CardTitle>
                    <CardDescription>Realize o login na sua conta</CardDescription>
                </CardHeader>
                <CardContent>
                    {isLogin ? <LoginForm /> : <RegisterForm />}
                </CardContent>
                <CardFooter>
                    <p>Não tem uma conta?</p><Button onClick={handleChange} className="text-card-foreground/60 underline">Crie uma!</Button>
                </CardFooter>
            </Card>
        </section>
    );
}

export default AuthenticationPage;