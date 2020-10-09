/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.framework;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.supercar.SupercarDealer;
import org.docksidestage.bizfw.di.cast.TooLazyDog;
import org.docksidestage.bizfw.di.container.SimpleDiContainer;
import org.docksidestage.bizfw.di.container.component.DiContainerModule;
import org.docksidestage.bizfw.di.nondi.NonDiDirectFirstAction;
import org.docksidestage.bizfw.di.nondi.NonDiDirectSecondAction;
import org.docksidestage.bizfw.di.usingdi.UsingDiAccessorAction;
import org.docksidestage.bizfw.di.usingdi.UsingDiWebFrameworkProcess;
import org.docksidestage.bizfw.di.usingdi.settings.UsingDiModule;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Dependency Injection (DI) as beginner level. <br>
 * Show answer by log() or write answer on comment for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step41DependencyInjectionBeginnerTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        Precondition
    //                                                                        ============
    /**
     * Search "Dependency Injection" by internet and learn it in thirty minutes. (study only) <br>
     * ("Dependency Injection" をインターネットで検索して、30分ほど学んでみましょう。(勉強のみ))
     */
    public void test_whatis_DependencyInjection() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // What is Dependency Injection?
        // - - - - - (your answer?)
        //
        //
        //
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                                 Non DI Code Reading
    //                                                                 ===================
    /**
     * What is the difference between NonDiDirectFirstAction and NonDiDirectSecondAction? <br>
     * (NonDiDirectFirstAction と NonDiDirectSecondAction の違いは？)
     */
    public void test_nondi_difference_between_first_and_second() {
        // your answer? => NonDiDirectSecondAction overrides the constructor for supercar, allowing for
        // an extra detail to be explicitly written out in the debug log (the value being what model)
        // This allows us to see for SecondAction that the key for steering wheel like sea is wrong (it should be sea not piari)
        NonDiDirectFirstAction test1 = new NonDiDirectFirstAction();
        NonDiDirectSecondAction test2 = new NonDiDirectSecondAction();

//        test1.goToOffice();
//        test2.goToOffice();
        // and your confirmation code here freely
    }

    /**
     * What is the difference between NonDiDirectSecondAction and NonDiFactoryMethodAction? <br>
     * (NonDiDirectSecondAction と NonDiFactoryMethodAction の違いは？)
     */
    public void test_nondi_difference_between_second_and_FactoryMethod() {
        // your answer? => Difference here is that the necessary extension seen in SecondAction is consolidated into
        // one extension written below all the methods in FactoryMethod. This is good because it reduces the amount of
        // boilerplate/spaghetti code we have to write.
        // and your confirmation code here freely
    }

    /**
     * What is the difference between NonDiFactoryMethodAction and NonDiIndividualFactoryAction? <br>
     * (NonDiFactoryMethodAction と NonDiIndividualFactoryAction の違いは？)
     */
    public void test_nondi_difference_between_FactoryMethod_and_IndividualFactory() {
        // your answer? => Extension related code has been written in another class called NonDiSupercarFactory, which is good
        // for cleanliness because all extensions can be written within that class while the customized version of the function
        // (that has the overridden functions) can be accessed quickly with a constructor in the original NonDiIndividualFactoryAction class
        // and your confirmation code here freely
    }

    // ===================================================================================
    //                                                               Using DI Code Reading
    //                                                               =====================
    /**
     * What is the difference between UsingDiAccessorAction and UsingDiAnnotationAction? <br>
     * (UsingDiAccessorAction と UsingDiAnnotationAction の違いは？)
     */
    public void test_usingdi_difference_between_Accessor_and_Annotation() {
        // your answer? => Annotation contains the @SimpleInjection annotation, which lets the compiler know (in this case)
        // that we can assume that an animal and supercard dealer exist. For Accessor, the members don't have the annotation,
        // meaning that without mutating or using a Setter function for the appropriate member, the other functions won't work
        // and your confirmation code here freely
    }

    /**
     * What is the difference between UsingDiAnnotationAction and UsingDiDelegatingAction? <br>
     * (UsingDiAnnotationAction と UsingDiDelegatingAction の違いは？)
     */
    public void test_usingdi_difference_between_Annotation_and_Delegating() {
        // your answer? => Delegating puts all of the required members (Animal, SupercarDealer) in a separate class. All the
        // functions refer to this delegating class, which contains inject annotation(s), so that the overarching UsingDiDelegationAction
        // class doesn't contain any direct dependencies that would fail to work.
        // and your confirmation code here freely
    }

    // ===================================================================================
    //                                                           Execute like WebFramework
    //                                                           =========================
    /**
     * Execute callFriend() of accessor action by UsingDiWebFrameworkProcess. (Animal as TooLazyDog) <br>
     * (accessor の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう (Animal は TooLazyDog として))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_accessor() {
        SimpleDiContainer container = SimpleDiContainer.getInstance();
        UsingDiModule module = new UsingDiModule();
        container.registerModule(module);
        container.resolveDependency();
        UsingDiWebFrameworkProcess tester = new UsingDiWebFrameworkProcess();
        tester.requestAccessorCallFriend();
    }

    /**
     * Execute callFriend() of annotation and delegating actions by UsingDiWebFrameworkProcess. <br>
     *  (Animal as TooLazyDog...so you can increase hit-points of sleepy cat in this method) <br>
     * <br>
     * (annotation, delegating の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう <br>
     *  (Animal は TooLazyDog として...ということで眠い猫のヒットポイントをこのメソッド内で増やしてもOK))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_annotation_delegating() {
        SimpleDiContainer container = SimpleDiContainer.getInstance();
        UsingDiModule module = new UsingDiModule();
        container.registerModule(module);
        container.resolveDependency();
        UsingDiWebFrameworkProcess tester = new UsingDiWebFrameworkProcess();
        tester.requestAnnotationCallFriend(); //nearly the same?
    }

    /**
     * What is concrete class of instance variable "animal" of UsingDiAnnotationAction? (when registering UsingDiModule) <br>
     * (UsingDiAnnotationAction のインスタンス変数 "animal" の実体クラスは？ (UsingDiModuleを登録した時))
     */
    public void test_usingdi_whatis_animal() {
        // your answer? => TooLazyDog

        // and your confirmation code here freely
        SimpleDiContainer container = SimpleDiContainer.getInstance();
        UsingDiModule module = new UsingDiModule();
        container.registerModule(module);
        container.resolveDependency();
        log(container.getComponent(Animal.class).getClass());
    }

    // ===================================================================================
    //                                                                        DI Container
    //                                                                        ============
    /**
     * What is DI container? <br>
     * (DIコンテナとは？)
     */
    public void test_whatis_DIContainer() {
        // your answer? => A singleton container that can hold different modules that allow for separated testing and scalability.
        // We can add different things to the componentMap and grab whatever we need to and call whatever action or annotation we need
        // to in order to reference a certain class and its methods. This allows us to work with fewer hard dependencies.
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Lasta Di application? <br>
     * (以下のLasta DiアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     * 
     * https://github.com/lastaflute/lastaflute-example-harbor
     */
    public void test_zone_search_component_on_LastaDi() {
        // your answer? => I'm guessing it takes place in CDIDBFluteModule.vm, as there seem to be annotation and injection related
        // functions and Class<?> component related things as well as containers.
    }

    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Spring application? <br>
     * (以下のSpringアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     *
     * https://github.com/dbflute-example/dbflute-example-on-springboot
     */
    public void test_zone_search_component_on_Spring() {
        // your answer? => I think it's: [1] mydbflute/dbflute-1.2.3/templates/om/java/allcommon/container/cdi/CDIDBFluteModule.vm
        //                               [2] mydbflute/dbflute-1.2.3/templates/om/java/allcommon/container/guice/GuideDBFluteModule.vm
        // for the same reasons as the previous question. It looks like all of the injection related work is happening inside these module files.
        // I looked up .vm and found out that it is used for generating class skeletons-- this means that the MemberBhv class is likely to be
        // generated by the information found in these module files.
    }
}
